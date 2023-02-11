package com.week2.easytask.signup.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.R
import com.week2.easytask.databinding.ActivitySignupkakaoBinding
import com.week2.easytask.signup.SignupSingleton

class SignupkakaoActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySignupkakaoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupkakaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SignupSingleton.state = "kakao"  

        // type 선택 frag부터 시작

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frag_signup_kakao, SignuptypeFragment())
            .commit()

    }
}