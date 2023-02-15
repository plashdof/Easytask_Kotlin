package com.week2.easytask

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.login.view.LoginActivity
import com.week2.easytask.webview.model.GetaccessTokenData
import com.week2.easytask.webview.model.GetaccessTokenResponse
import com.week2.easytask.webview.network.GetaccessTokenAPI
import com.week2.easytask.webview.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPreferences : SharedPreferences
    private val GetaccessTokenRetro = Retrofit.getloginInstance().create(GetaccessTokenAPI::class.java)

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
            val datas = GetaccessTokenData(Singleton.id.toInt(),Singleton.refreshToken)
            GetaccessTokenRetro.getaccessToken(datas)
                .enqueue(object: Callback<GetaccessTokenResponse>{
                    override fun onResponse(
                        call: Call<GetaccessTokenResponse>,
                        response: Response<GetaccessTokenResponse>
                    ) {
                        Log.d("API결과", "${response.body()}")

                        if(response.code() == 200){
                            Singleton.accessToken = response.body()!!.accessToken

                            val intent = Intent(this@SplashActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }

                    override fun onFailure(call: Call<GetaccessTokenResponse>, t: Throwable) {
                    }
                })

        }
    }
}