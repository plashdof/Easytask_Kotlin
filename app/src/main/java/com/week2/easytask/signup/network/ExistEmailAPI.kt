package com.week2.easytask.signup.network

import com.week2.easytask.signup.model.ExistEmailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ExistEmailAPI {

    @GET("/users/email/{email}/exist")
    fun existemail(
        @Path("email") email : String
    ): Call<ExistEmailResponse>
}