package com.example.finalapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.finalapplication.databinding.ActivityAuthBinding
import com.example.finalapplication.databinding.ActivityMainBinding

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEmail.setOnClickListener {
            val intent = Intent(this, EmailActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            MyApplication.auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) {task ->
                    binding.email.text.clear()
                    binding.password.text.clear()
                    if (task.isSuccessful) {
                        if (MyApplication.checkAuth()) {
                            MyApplication.email = email
                            finish()
                        }
                        else {
                            Toast.makeText(baseContext, "이메일 인증 실패...", Toast.LENGTH_LONG).show()
                        }
                    }
                    else {
                        Toast.makeText(baseContext, "로그인 실패...", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}