package com.week2.easytask.signup.network

import com.week2.easytask.signup.model.CheckCompanynumData
import com.week2.easytask.signup.model.CheckCompanynumResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface CheckCompanynumAPI {

    @POST("api/nts-businessman/v1/status")
    fun checkcompanynum(
        @Query("serviceKey") serviceKey : String,
        @Query("resultType") resultType : String,
        @Body params : CheckCompanynumData
    ): Call<CheckCompanynumResponse>
}