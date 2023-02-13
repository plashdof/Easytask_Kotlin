package com.week2.easytask.webview.view

import android.content.Intent
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.Retrofit
import com.week2.easytask.Singleton
import com.week2.easytask.databinding.ActivityMainBinding
import com.week2.easytask.login.view.LoginActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.webview.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.useWideViewPort = true

        }

        binding.webview.loadUrl("http://service.easytask.kr/external-login?userId=${Singleton.id}&token=${Singleton.accessToken}")

    }

    override fun onBackPressed() {

        if(binding.webview.canGoBack()){
            binding.webview.goBack()
        }else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}