package com.week2.easytask

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    val mainurl = "https://gateway.easytask.kr/"
    val companynumcheckurl = "https://api.odcloud.kr/"

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

}