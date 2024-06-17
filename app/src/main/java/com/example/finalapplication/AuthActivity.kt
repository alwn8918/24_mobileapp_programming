package com.example.finalapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


    }
}