package com.example.finalapplication

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.util.TypedValue
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.example.finalapplication.databinding.ActivityAuthBinding
import com.example.finalapplication.databinding.ActivityDetailBinding
import com.google.android.gms.maps.MapFragment

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // IntroFragment
        val introfragment = IntroFragment()
        val reviewfragment = ReviewFragment()
        val mapsfragment = MapFragment()
        val bundle = Bundle()

        binding.btnIntro.setOnClickListener {
            binding.btnIntro.setTextColor(Color.parseColor("#FFC436"))
            binding.btnReview.setTextColor(Color.parseColor("#FF000000"))

            val id = intent.getStringExtra("id")
            bundle.putString("contentid", id)
            Log.d("mobileApp", "$id")

            introfragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.intro_review, introfragment)
                .commit()
        }

        binding.btnReview.setOnClickListener {
            binding.btnReview.setTextColor(Color.parseColor("#FFC436"))
            binding.btnIntro.setTextColor(Color.parseColor("#FF000000"))

            val id = intent.getStringExtra("id")
            bundle.putString("contentid", id)
            Log.d("mobileApp", "$id")

            reviewfragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.intro_review, reviewfragment)
                .commit()
        }

        val x = intent.getStringExtra("x")
        val y = intent.getStringExtra("y")
        binding.address.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + y + "," + x))
            startActivity(intent)
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

        val id = intent.getStringExtra("id")
        binding.contentid.text = id

        getSupportActionBar()?.setTitle(Html.fromHtml("<font color='#FFFFFF'>" + name + "</font>"));

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