package com.example.caregiver.ui.viewmodel

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
    val isLoginSuccess: Boolean = false
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
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoginSuccess = true
                )
            } else {
                val exception = result.exceptionOrNull() ?: Exception("Login failed")
                val errorMsg = when {
                    exception.message?.contains("INVALID_EMAIL") == true -> "Invalid email format"
                    exception.message?.contains("USER_NOT_FOUND") == true -> "User not found"
                    exception.message?.contains("WRONG_PASSWORD") == true -> "Wrong password"
                    exception.message?.contains("network") == true -> "Network error. Please check your connection"
                    else -> exception.message ?: "Login failed"
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

