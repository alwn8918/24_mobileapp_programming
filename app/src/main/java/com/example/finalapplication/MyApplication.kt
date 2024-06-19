package com.example.finalapplication

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class MyApplication : MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth
        var email: String? = null
        lateinit var db: FirebaseFirestore
        lateinit var storage: FirebaseStorage

        fun checkAuth(): Boolean {
            // Firebase 인증 객체가 초기화되었는지 확인
            if (!::auth.isInitialized) {
                // 초기화되지 않았다면 초기화
                auth = Firebase.auth
            }

            // 현재 사용자 가져오기
            val currentUser = auth.currentUser
            if (currentUser != null) {
                email = currentUser.email
                return currentUser.isEmailVerified
            }
            return false
        }
    }

    override fun onCreate() {
        super.onCreate()

        // Firebase 인증 객체 초기화
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage
    }
}
