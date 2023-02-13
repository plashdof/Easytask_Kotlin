package com.week2.easytask.webview.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.Retrofit
import com.week2.easytask.Singleton
import com.week2.easytask.databinding.ActivityMainBinding
import com.week2.easytask.login.view.LoginActivity
import com.week2.easytask.webview.model.AfterSigninResponse
import com.week2.easytask.webview.network.AfterSigninAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var AfterSigninRetro = Retrofit.getloginInstance().create(AfterSigninAPI::class.java)
    private val CLIENT_URL = "https://service.easytask.kr/client/mypage/mytask?requestStatus&sdateSameOrBigger&sdateSameOrSmaller&searchQuery&page=1"
    private val IRUMI_URL = "https://service.easytask.kr/irumi/mypage/mytask"

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

        AfterSigninRetro.aftersignin("Bearer "+ Singleton.accessToken,Singleton.id)
            .enqueue(object: Callback<AfterSigninResponse>{
                override fun onResponse(
                    call: Call<AfterSigninResponse>,
                    response: Response<AfterSigninResponse>
                ) {
                    if(response.code() == 200){
                        if(response.body()!!.roleList[0].description == "고객"){
                            binding.webview.apply {

                                val cookieManager = CookieManager.getInstance()
                                cookieManager.removeAllCookies(null)
                                cookieManager.setCookie(CLIENT_URL,"userId" + "=" + Singleton.id)
                                cookieManager.setCookie(CLIENT_URL,"token" + "=" + Singleton.accessToken)
                                cookieManager.setAcceptThirdPartyCookies(binding.webview,true)
                            }
                            binding.webview.loadUrl(CLIENT_URL)

                        }else{
                            binding.webview.apply {
                                val cookieManager = CookieManager.getInstance()
                                cookieManager.removeAllCookies(null)
                                cookieManager.setCookie(IRUMI_URL,"userId" + "=" + Singleton.id)
                                cookieManager.setCookie(IRUMI_URL,"token" + "=" + Singleton.accessToken)
                                cookieManager.setAcceptThirdPartyCookies(binding.webview,true)
                            }

                            binding.webview.loadUrl(IRUMI_URL)
                        }
                    }
                }

                override fun onFailure(call: Call<AfterSigninResponse>, t: Throwable) {
                }
            })


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