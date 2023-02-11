package com.week2.easytask

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.databinding.ActivityMainBinding
import com.week2.easytask.login.view.LoginActivity
import okhttp3.HttpUrl


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

        binding.webview.loadUrl("https://service.easytask.kr/client/mypage/mytask?requestStatus&sdateSameOrBigger&sdateSameOrSmaller&searchQuery&page=1")
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