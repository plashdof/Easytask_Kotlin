package com.week2.easytask.login.network

import com.week2.easytask.login.model.SendEmailData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT

interface SendEmailAPI {

    @PUT("/api/util/mail/send-mail")
    fun sendeamil(
        @Body params : SendEmailData
    ): Call<SendEmailData>
}