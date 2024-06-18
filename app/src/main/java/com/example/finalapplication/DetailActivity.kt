package com.example.finalapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import com.bumptech.glide.Glide
import com.example.finalapplication.databinding.ActivityAuthBinding
import com.example.finalapplication.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // IntroFragment
        val introfragment = IntroFragment()
        val bundle = Bundle()

        binding.btnIntro.setOnClickListener {
            binding.btnIntro.setTextColor(Color.parseColor("#0174BE"))
            val id = intent.getStringExtra("id")
            bundle.putString("contentid", id)
            Log.d("mobileApp", "$id")

            introfragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.intro, introfragment)
                .commit()
        }

        val image = intent.getStringExtra("image")
        Glide.with(binding.root)
            .load(image)
            .into(binding.image)

        val type = intent.getStringExtra("type")
        binding.type.text = type

        val name = intent.getStringExtra("name")
        binding.name.text = name

        val address = intent.getStringExtra("address")
        binding.address.text = address

    }
}