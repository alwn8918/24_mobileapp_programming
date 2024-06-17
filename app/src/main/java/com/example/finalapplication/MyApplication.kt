package com.example.finalapplication

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyApplication: MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth
        var email: String? = null

        fun checkAuth(): Boolean {
            var currentUser = auth.currentUser
            if (currentUser != null) {
                email = currentUser.email
                return currentUser.isEmailVerified
            }
            return false
        }
    }

    override fun onCreate() {
        super.onCreate()

        auth = Firebase.auth
    }
}