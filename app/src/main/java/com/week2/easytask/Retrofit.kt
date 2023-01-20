package com.week2.easytask

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    val mainurl = "https://gateway.easytask.kr/"
    val buildRetro = Retrofit.Builder()
        .baseUrl(mainurl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getInstance() : Retrofit {
        return buildRetro
    }

}