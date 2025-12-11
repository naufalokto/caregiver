package com.example.caregiver

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.caregiver.ui.screens.LoginScreen
import com.example.caregiver.ui.theme.CaregiverTheme
import com.example.caregiver.ui.viewmodel.LoginViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaregiverTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: LoginViewModel = viewModel()
                    val uiState by viewModel.uiState.collectAsState()
                    
                    LoginScreen(
                        viewModel = viewModel,
                        onLoginSuccess = {
                            // Navigation will be handled by LaunchedEffect below
                        },
                        onCreateAccountClick = {
                            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                            startActivity(intent)
                        }
                    )
                    
                    // Handle navigation after successful login
                    LaunchedEffect(uiState.isLoginSuccess) {
                        if (uiState.isLoginSuccess && uiState.username != null) {
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java).apply {
                                putExtra("username", uiState.username)
                            }
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }
}

