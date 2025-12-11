package com.example.caregiver

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.caregiver.ui.screens.LoginScreen
import com.example.caregiver.ui.theme.CaregiverTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaregiverTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onLoginSuccess = {
                            // TODO: Navigate to home screen after successful login
                            // For now, just show success
                        },
                        onCreateAccountClick = {
                            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

