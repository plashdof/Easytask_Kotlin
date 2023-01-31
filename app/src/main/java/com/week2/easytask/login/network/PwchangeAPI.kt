package com.week2.easytask.login.network

import com.week2.easytask.login.model.PwchangeData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT

interface PwchangeAPI {
    @PUT("/api/util/mail/reset-password")
    fun pwchange(
        @Body params : PwchangeData
    ): Call<PwchangeData>
}