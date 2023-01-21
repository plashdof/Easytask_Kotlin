package com.week2.easytask.login.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.databinding.ActivityFindidsuccessBinding

class FindidSuccessActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFindidsuccessBinding
    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindidsuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("email")){
            email = intent.getStringExtra("email").toString()
            binding.tvEmail.text = email
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
                .putExtra("email", email)
            startActivity(intent)
        }
    }
}