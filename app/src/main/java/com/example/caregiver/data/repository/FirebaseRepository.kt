package com.example.caregiver.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // User Authentication
    suspend fun loginUser(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registerUser(
        email: String,
        password: String,
        username: String,
        phoneNumber: String? = null,
        gender: String? = null
    ): Result<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            // Save user data to Firestore
            val userData = hashMapOf(
                "username" to username,
                "email" to email,
                "createdAt" to com.google.firebase.Timestamp.now()
            )
            phoneNumber?.let { userData["phoneNumber"] = it }
            gender?.let { userData["gender"] = it }
            db.collection("users").document(result.user?.uid ?: "").set(userData).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
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

