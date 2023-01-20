package com.week2.easytask.login.network

import com.week2.easytask.login.model.SigninResponse
import com.week2.easytask.login.model.SigninData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SigninAPI {

    @POST("/auth/sign-in")
    fun signin(
        @Body params : SigninData
    ): Call<SigninResponse>
}