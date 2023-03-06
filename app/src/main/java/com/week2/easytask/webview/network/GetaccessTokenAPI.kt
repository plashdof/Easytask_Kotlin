package com.week2.easytask.webview.network

import com.week2.easytask.webview.model.GetaccessTokenData
import com.week2.easytask.webview.model.GetaccessTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GetaccessTokenAPI {
    @POST("/api/v2/auth/user/token/refresh")
    fun getaccessToken(
        @Body params: GetaccessTokenData
    ):Call<GetaccessTokenResponse>
}