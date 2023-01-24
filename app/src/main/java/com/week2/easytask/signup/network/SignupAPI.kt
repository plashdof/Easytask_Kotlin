package com.week2.easytask.signup.network

import com.week2.easytask.login.model.SigninResponse
import com.week2.easytask.signup.model.SignupData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupAPI {

    @POST("/auth/sign-up")
    fun signup(
        @Body params : SignupData
    ) : Call<SigninResponse>

}