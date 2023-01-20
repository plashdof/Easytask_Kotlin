package com.week2.easytask.login.network

import com.week2.easytask.login.model.FindpwResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FindpwAPI {

    @GET("/users")
    fun findpw(
        @Query("email") email : String
    ): Call<FindpwResponse>
}