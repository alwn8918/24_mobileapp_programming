package com.example.finalapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalapplication.databinding.ActivityAuthBinding
import com.example.finalapplication.databinding.ActivityEmailBinding

class EmailActivity : AppCompatActivity() {
    lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}