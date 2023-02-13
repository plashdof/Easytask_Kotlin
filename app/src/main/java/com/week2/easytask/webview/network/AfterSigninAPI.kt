package com.week2.easytask.webview.network

import com.week2.easytask.Singleton
import com.week2.easytask.webview.model.AfterSigninResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface AfterSigninAPI {

    @GET("api/common/users/auth/info/{userID}")
    fun aftersignin(
        @Header("Authorization") Authorization : String,
        @Path("userID") userID : String
    ): Call<AfterSigninResponse>
}