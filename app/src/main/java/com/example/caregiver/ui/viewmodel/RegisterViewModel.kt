package com.example.caregiver.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caregiver.data.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRegisterSuccess: Boolean = false
)

class RegisterViewModel : ViewModel() {
    private val repository = FirebaseRepository()
    
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun registerUser(
        username: String,
        email: String,
        phoneNumber: String,
        gender: String,
        password: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            // Validation
            when {
                username.isBlank() -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Username is required"
                    )
                    return@launch
                }
                email.isBlank() -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Email is required"
                    )
                    return@launch
                }
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Invalid email format"
                    )
                    return@launch
                }
                phoneNumber.isBlank() -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Phone number is required"
                    )
                    return@launch
                }
                gender.isBlank() -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Gender is required"
                    )
                    return@launch
                }
                password.isBlank() -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Password is required"
                    )
                    return@launch
                }
                password.length < 6 -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Password must be at least 6 characters"
                    )
                    return@launch
                }
            }
            
            val result = repository.registerUser(email, password, username, phoneNumber, gender)
            if (result.isSuccess) {
                Log.d("RegisterViewModel", "Registration successful")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRegisterSuccess = true
                )
            } else {
                val exception = result.exceptionOrNull() ?: Exception("Registration failed")
                Log.e("RegisterViewModel", "Registration failed: ${exception.message}")
                Log.e("RegisterViewModel", "Exception: ${exception.javaClass.name}")
                exception.printStackTrace()
                
                val errorMsg = when {
                    exception.message?.contains("EMAIL_ALREADY_IN_USE", ignoreCase = true) == true -> "Email already registered"
                    exception.message?.contains("INVALID_EMAIL", ignoreCase = true) == true -> "Invalid email format"
                    exception.message?.contains("WEAK_PASSWORD", ignoreCase = true) == true -> "Password is too weak"
                    exception.message?.contains("end of stream", ignoreCase = true) == true -> "Connection interrupted. Please check your internet and try again"
                    exception.message?.contains("network", ignoreCase = true) == true -> "Network error. Please check your connection"
                    exception.message?.contains("PERMISSION_DENIED", ignoreCase = true) == true -> "Permission denied. Please check Firestore rules"
                    exception.message?.contains("Missing or insufficient permissions", ignoreCase = true) == true -> "Permission denied. Please check Firestore rules"
                    exception.message?.contains("socket", ignoreCase = true) == true -> "Connection error. Please check your internet connection"
                    exception.message?.contains("EPERM", ignoreCase = true) == true -> "Permission error. Please check Firestore rules or internet connection"
                    exception.message?.contains("UNAVAILABLE", ignoreCase = true) == true -> "Service unavailable. Please try again later"
                    exception.message?.contains("timeout", ignoreCase = true) == true -> "Request timeout. Please check your internet connection"
                    else -> "Error: ${exception.message ?: exception.toString()}"
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = errorMsg
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

