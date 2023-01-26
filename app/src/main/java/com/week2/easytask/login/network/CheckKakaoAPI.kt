package com.week2.easytask.login.network

import com.week2.easytask.login.model.CheckKakaoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CheckKakaoAPI {
    @GET("/api/user/kakao/{kakaoid}")
    fun checkkakao(
        @Path("kakaoid") kakaoid : String
    ): Call<CheckKakaoResponse>
}