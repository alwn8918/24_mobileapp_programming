package com.example.finalapplication

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.PreferenceManager
import com.example.finalapplication.databinding.ActivityAuthBinding
import com.example.finalapplication.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.play.integrity.internal.c
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEmail.setOnClickListener {
            val intent = Intent(this, EmailActivity::class.java)
            startActivity(intent)
        }

        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            Log.d("mobileApp", "account: $task")
            try {
                val account = task.getResult(ApiException::class.java)
                val crendential = GoogleAuthProvider.getCredential(account.idToken, null)
                MyApplication.auth.signInWithCredential(crendential)
                    .addOnCompleteListener(this) {task ->
                        if (task.isSuccessful) {
                            MyApplication.email = account.email
                            Toast.makeText(baseContext, "구글 로그인 성공!", Toast.LENGTH_LONG).show()
                            finish()
                        }
                        else {
                            Toast.makeText(baseContext, "구글 로그인 실패...", Toast.LENGTH_LONG).show()
                        }
                    }
            } catch (e: ApiException) {
                Toast.makeText(baseContext, "구글 로그인 Exception", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnGoogle.setOnClickListener {
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_client_id))
                .requestEmail()
                .build()
            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            requestLauncher.launch(signInIntent)
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

    // 설정에서 값 바꾸면 바로 적용
    override fun onResume() {
        super.onResume()

        // sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val color = sharedPreferences.getString("color", "#0174BE")
        binding.title.setTextColor(Color.parseColor(color))

        if(color == "#365E32") {
            binding.btnLogin.setBackgroundResource(R.drawable.green_background)
        }
        if(color == "#222222") {
            binding.btnLogin.setBackgroundResource(R.drawable.black_background)
        }

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor(color)))

    }

}