package com.example.finalapplication

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.example.finalapplication.databinding.ActivityAuthBinding
import com.example.finalapplication.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var sharedPreferences: SharedPreferences

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

    // 설정에서 값 바꾸면 바로 적용
    override fun onResume() {
        super.onResume()

        // sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val color = sharedPreferences.getString("color", "#0174BE")

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(color)))

    }
}