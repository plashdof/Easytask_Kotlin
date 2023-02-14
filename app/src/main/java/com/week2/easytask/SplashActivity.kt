package com.week2.easytask

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.login.view.LoginActivity
import com.week2.easytask.webview.view.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("test", MODE_PRIVATE)

        Handler().postDelayed({

            autoLogin()

        },1000)

    }

    // 자동로그인

    fun autoLogin(){
        Singleton.id = sharedPreferences.getString("ID","ERROR").toString()
        Singleton.accessToken = sharedPreferences.getString("accessToken","ERROR").toString()
        Singleton.refreshToken = sharedPreferences.getString("refreshToken","ERROR").toString()

        Log.d("API결과","${Singleton.accessToken} ")

        if(Singleton.accessToken == "ERROR"){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}