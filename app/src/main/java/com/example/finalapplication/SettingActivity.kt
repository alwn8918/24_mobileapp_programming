package com.example.finalapplication

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager

class SettingActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
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