package com.week2.easytask.signup.model

import com.google.gson.annotations.SerializedName

data class CheckCompanynumResponse(
    @SerializedName("request_cnt") val request_cnt : Int,
    @SerializedName("match_cnt") val match_cnt : Int,
    @SerializedName("status_code") val status_code : String,
    @SerializedName("data") val data : ArrayList<CompanyData>
)

data class CompanyData(
    @SerializedName("b_no") val b_no : String,
    @SerializedName("b_stt") val b_stt : String,
    @SerializedName("b_stt_cd") val b_stt_cd : String
)
