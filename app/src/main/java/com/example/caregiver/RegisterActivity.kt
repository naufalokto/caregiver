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
import com.example.caregiver.ui.screens.RegisterScreen
import com.example.caregiver.ui.theme.CaregiverTheme
import com.example.caregiver.ui.viewmodel.RegisterViewModel

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaregiverTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: RegisterViewModel = viewModel()
                    val uiState by viewModel.uiState.collectAsState()

                    RegisterScreen(
                        viewModel = viewModel,
                        onRegisterClick = { _, _, _, _, _ -> },
                        onLoginClick = {
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    )

                    // Handle registration success
                    LaunchedEffect(uiState.isRegisterSuccess) {
                        if (uiState.isRegisterSuccess) {
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }
}

