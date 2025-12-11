package com.example.caregiver.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caregiver.data.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoginSuccess: Boolean = false,
    val username: String? = null
)

class LoginViewModel : ViewModel() {
    private val repository = FirebaseRepository()
    
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            if (email.isBlank() || password.isBlank()) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Please fill in all fields"
                )
                return@launch
            }
            
            val result = repository.loginUser(email, password)
            if (result.isSuccess) {
                // Get user data after successful login
                val currentUser = repository.getCurrentUser()
                val username = if (currentUser != null) {
                    val userDataResult = repository.getUserData(currentUser.uid)
                    if (userDataResult.isSuccess) {
                        userDataResult.getOrNull()?.get("username") as? String ?: "User"
                    } else {
                        "User"
                    }
                } else {
                    "User"
                }
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoginSuccess = true,
                    username = username
                )
            } else {
                val exception = result.exceptionOrNull() ?: Exception("Login failed")
                Log.e("LoginViewModel", "Login failed: ${exception.message}")
                Log.e("LoginViewModel", "Exception: ${exception.javaClass.name}")
                exception.printStackTrace()
                
                val errorMsg = when {
                    exception.message?.contains("INVALID_EMAIL", ignoreCase = true) == true -> "Invalid email format"
                    exception.message?.contains("USER_NOT_FOUND", ignoreCase = true) == true -> "User not found"
                    exception.message?.contains("WRONG_PASSWORD", ignoreCase = true) == true -> "Wrong password"
                    exception.message?.contains("credential", ignoreCase = true) == true -> "Invalid email or password"
                    exception.message?.contains("incorrect", ignoreCase = true) == true -> "Invalid email or password"
                    exception.message?.contains("malformed", ignoreCase = true) == true -> "Invalid email or password"
                    exception.message?.contains("expired", ignoreCase = true) == true -> "Session expired. Please try again"
                    exception.message?.contains("end of stream", ignoreCase = true) == true -> "Connection interrupted. Please check your internet and try again"
                    exception.message?.contains("network", ignoreCase = true) == true -> "Network error. Please check your connection"
                    exception.message?.contains("timeout", ignoreCase = true) == true -> "Request timeout. Please check your internet connection"
                    exception.message?.contains("UNAVAILABLE", ignoreCase = true) == true -> "Service unavailable. Please try again later"
                    else -> "Error: ${exception.message ?: exception.toString()}"
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = errorMsg
                )
            }
        }
    }

    fun registerUser(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            val email = "$username@caregiver.com" // Temporary: convert username to email
            
            val result = repository.registerUser(email, password, username)
            if (result.isSuccess) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoginSuccess = true
                )
            } else {
                val exception = result.exceptionOrNull() ?: Exception("Registration failed")
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = exception.message ?: "Registration failed"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

