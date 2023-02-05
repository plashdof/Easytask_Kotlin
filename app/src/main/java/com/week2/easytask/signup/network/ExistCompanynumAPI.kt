package com.week2.easytask.signup.network

import com.week2.easytask.signup.model.ExistCompanynumResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExistCompanynumAPI {

    @GET("companies/business-number/{num}/exists")
    fun existcompanynum(
        @Path("num") num : String
    ): Call<ExistCompanynumResponse>
}

