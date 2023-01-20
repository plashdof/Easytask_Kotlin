package com.week2.easytask.login.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.databinding.ActivityFindpwsuccessBinding

class FindpwSuccessActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFindpwsuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindpwsuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}