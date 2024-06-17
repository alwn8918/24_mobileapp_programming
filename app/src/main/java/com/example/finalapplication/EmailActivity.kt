package com.example.finalapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.finalapplication.databinding.ActivityAuthBinding
import com.example.finalapplication.databinding.ActivityEmailBinding

class EmailActivity : AppCompatActivity() {
    lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeVisibility(intent.getStringExtra("status").toString())

        binding.btnSign.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            MyApplication.auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) {task ->
                    binding.email.text.clear()
                    binding.password.text.clear()
                    if (task.isSuccessful) {
                        MyApplication.auth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener{sendTask ->
                                if(sendTask.isSuccessful) {
                                    Toast.makeText(baseContext, "회원가입 성공! 메일을 확인해 주세요", Toast.LENGTH_LONG).show()
                                    changeVisibility("logout")
                                }
                                else {
                                    Toast.makeText(baseContext, "회원가입 실패...", Toast.LENGTH_LONG).show()
                                    changeVisibility("logout")
                                }
                            }
                    }
                    else {
                        Toast.makeText(baseContext, "회원가입 실패...", Toast.LENGTH_LONG).show()
                        changeVisibility("logout")
                    }
                }
        }


    }

    fun changeVisibility(mode:String) {
        if (mode.equals("login")) {

        }
        else if (mode.equals("logout")) {

        }
        else if (mode.equals("signup")) {

        }
    }
}