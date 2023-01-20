package com.week2.easytask.login.network

import com.week2.easytask.login.model.FindemailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FindemailAPI {

    @GET("/users/email")
    fun findemail(
        @Query("name") name : String,
        @Query("phoneNumber") phoneNumber : String
    ): Call<FindemailResponse>
}