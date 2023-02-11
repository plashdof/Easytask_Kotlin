package com.week2.easytask

import okhttp3.CookieJar
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit

object Retrofit {

    val mainurl = "https://gateway.easytask.kr/"
    val companynumcheckurl = "https://api.odcloud.kr/"

    private var instance: Retrofit? = null

    val buildRetro = Retrofit.Builder()
        .baseUrl(mainurl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val buildcnRetro = Retrofit.Builder()
        .baseUrl(companynumcheckurl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getInstance() : Retrofit {
        return buildRetro
    }

    fun getcnInstance() : Retrofit{
        return buildcnRetro
    }

    fun getloginInstance() : Retrofit{

        if(instance == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .cookieJar(JavaNetCookieJar(CookieManager()))
                .addInterceptor(interceptor)
                .connectTimeout(20000L, TimeUnit.SECONDS)
                .build()

            instance = Retrofit.Builder()
                .baseUrl(mainurl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        return instance!!
    }

}