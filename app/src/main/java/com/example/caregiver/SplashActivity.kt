package com.example.caregiver

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import com.example.caregiver.ui.screens.SplashScreen
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen()
            
            // Delay 3 detik kemudian pindah ke LoginActivity
            LaunchedEffect(Unit) {
                delay(3000) // 3000 milliseconds = 3 detik
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

