package com.example.caregiver

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp

class CaregiverApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Ensure Firebase is initialized
        try {
            if (FirebaseApp.getApps(this).isEmpty()) {
                FirebaseApp.initializeApp(this)
                Log.d("CaregiverApplication", "Firebase initialized successfully")
            } else {
                Log.d("CaregiverApplication", "Firebase already initialized")
            }
            
            // Verify Firebase configuration
            val firebaseApp = FirebaseApp.getInstance()
            Log.d("CaregiverApplication", "Firebase App Name: ${firebaseApp.name}")
            Log.d("CaregiverApplication", "Firebase Options Project ID: ${firebaseApp.options.projectId}")
        } catch (e: Exception) {
            Log.e("CaregiverApplication", "Firebase initialization error: ${e.message}", e)
        }
    }
}
