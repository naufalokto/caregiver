package com.example.caregiver

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.caregiver.ui.screens.HomeScreen
import com.example.caregiver.ui.theme.CaregiverTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val username = intent.getStringExtra("username") ?: "User"
        
        setContent {
            CaregiverTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen(
                        username = username,
                        onCaregiverClick = {
                            // TODO: Navigate to Caregiver mode screen
                        },
                        onDisabilityClick = {
                            // TODO: Navigate to Disability mode screen
                        }
                    )
                }
            }
        }
    }
}
