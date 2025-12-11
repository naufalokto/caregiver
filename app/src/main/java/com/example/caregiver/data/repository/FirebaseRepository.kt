package com.example.caregiver.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

class FirebaseRepository {
    private val auth: FirebaseAuth by lazy {
        try {
            FirebaseAuth.getInstance().also {
                Log.d("FirebaseRepository", "FirebaseAuth initialized")
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "FirebaseAuth initialization error: ${e.message}", e)
            throw e
        }
    }
    
    private val db: FirebaseFirestore by lazy {
        try {
            FirebaseFirestore.getInstance().also {
                Log.d("FirebaseRepository", "FirebaseFirestore initialized")
            }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "FirebaseFirestore initialization error: ${e.message}", e)
            throw e
        }
    }

    // User Authentication
    suspend fun loginUser(email: String, password: String): Result<Unit> {
        var retryCount = 0
        val maxRetries = 3
        
        while (retryCount < maxRetries) {
            try {
                Log.d("FirebaseRepository", "Starting login for email: $email (attempt ${retryCount + 1}/$maxRetries)")
                auth.signInWithEmailAndPassword(email, password).await()
                Log.d("FirebaseRepository", "Login successful")
                return Result.success(Unit)
            } catch (e: Exception) {
                retryCount++
                
                // Check if it's a credential error (should not retry)
                val isCredentialError = e.message?.contains("credential", ignoreCase = true) == true ||
                        e.message?.contains("incorrect", ignoreCase = true) == true ||
                        e.message?.contains("malformed", ignoreCase = true) == true ||
                        e.message?.contains("expired", ignoreCase = true) == true ||
                        e.message?.contains("WRONG_PASSWORD", ignoreCase = true) == true ||
                        e.message?.contains("INVALID_EMAIL", ignoreCase = true) == true ||
                        e.message?.contains("USER_NOT_FOUND", ignoreCase = true) == true ||
                        e.javaClass.simpleName.contains("InvalidCredentials", ignoreCase = true)
                
                // Check if it's a network error (should retry)
                val isNetworkError = !isCredentialError && (
                        e.message?.contains("end of stream", ignoreCase = true) == true ||
                        e.message?.contains("network", ignoreCase = true) == true ||
                        e.message?.contains("timeout", ignoreCase = true) == true ||
                        e.message?.contains("unexpected", ignoreCase = true) == true ||
                        e.message?.contains("UNAVAILABLE", ignoreCase = true) == true
                )
                
                Log.e("FirebaseRepository", "Login error (attempt $retryCount): ${e.message}")
                Log.e("FirebaseRepository", "Error type: ${e.javaClass.simpleName}")
                
                // Don't retry for credential errors
                if (isCredentialError) {
                    Log.d("FirebaseRepository", "Credential error detected, not retrying")
                    return Result.failure(e)
                }
                
                // Retry only for network errors
                if (isNetworkError && retryCount < maxRetries) {
                    val delayTime = retryCount * 3000L // Progressive delay: 3s, 6s, 9s
                    Log.d("FirebaseRepository", "Network error detected, retrying in ${delayTime/1000} seconds...")
                    delay(delayTime) // Progressive delay before retry
                    continue
                } else {
                    // If it's not a network error or max retries reached, return failure
                    return Result.failure(e)
                }
            }
        }
        
        return Result.failure(Exception("Login failed after $maxRetries attempts"))
    }

    suspend fun registerUser(
        email: String,
        password: String,
        username: String,
        phoneNumber: String? = null,
        gender: String? = null
    ): Result<Unit> {
        var retryCount = 0
        val maxRetries = 3
        
        while (retryCount < maxRetries) {
            try {
                Log.d("FirebaseRepository", "Starting registration for email: $email (attempt ${retryCount + 1}/$maxRetries)")
                
                // Create user in Firebase Auth
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val userId = result.user?.uid
                Log.d("FirebaseRepository", "User created in Auth, UID: $userId")
                
                if (userId == null) {
                    Log.e("FirebaseRepository", "User ID is null after registration")
                    return Result.failure(Exception("Failed to create user account"))
                }
                
                // Verify user is authenticated before writing to Firestore
                val currentUser = auth.currentUser
                if (currentUser == null) {
                    Log.e("FirebaseRepository", "Current user is null after registration")
                    return Result.failure(Exception("User authentication failed"))
                }
                Log.d("FirebaseRepository", "Current user authenticated: ${currentUser.uid}")
                
                // Save user data to Firestore
                val userData = hashMapOf(
                    "username" to username,
                    "email" to email,
                    "createdAt" to com.google.firebase.Timestamp.now()
                )
                phoneNumber?.let { userData["phoneNumber"] = it }
                gender?.let { userData["gender"] = it }
                
                Log.d("FirebaseRepository", "Saving user data to Firestore: $userData")
                db.collection("users").document(userId).set(userData).await()
                Log.d("FirebaseRepository", "User data saved successfully")
                
                return Result.success(Unit)
            } catch (e: Exception) {
                retryCount++
                val isNetworkError = e.message?.contains("end of stream", ignoreCase = true) == true ||
                        e.message?.contains("network", ignoreCase = true) == true ||
                        e.message?.contains("timeout", ignoreCase = true) == true ||
                        e.message?.contains("unexpected", ignoreCase = true) == true
                
                Log.e("FirebaseRepository", "Registration error (attempt $retryCount): ${e.message}")
                Log.e("FirebaseRepository", "Error type: ${e.javaClass.simpleName}")
                
                if (isNetworkError && retryCount < maxRetries) {
                    val delayTime = retryCount * 3000L // Progressive delay: 3s, 6s, 9s
                    Log.d("FirebaseRepository", "Network error detected, retrying in ${delayTime/1000} seconds...")
                    delay(delayTime) // Progressive delay before retry
                    continue
                } else {
                    // If it's not a network error or max retries reached, return failure
                    return Result.failure(e)
                }
            }
        }
        
        return Result.failure(Exception("Registration failed after $maxRetries attempts"))
    }

    // Firestore Operations
    suspend fun saveUserData(userId: String, data: Map<String, Any>): Result<Unit> {
        return try {
            db.collection("users").document(userId).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserData(userId: String): Result<Map<String, Any>> {
        return try {
            val documentSnapshot = db.collection("users").document(userId).get().await()
            val resultData: Map<String, Any> = if (documentSnapshot.exists() && documentSnapshot.data != null) {
                // Convert document data to Map explicitly with proper type casting
                HashMap<String, Any>(documentSnapshot.data as Map<String, Any>)
            } else {
                emptyMap()
            }
            Result.success(resultData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUser() = auth.currentUser
    fun isUserLoggedIn() = auth.currentUser != null
    fun logout() = auth.signOut()
}

