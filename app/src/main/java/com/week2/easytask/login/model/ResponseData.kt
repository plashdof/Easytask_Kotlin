package com.week2.easytask.login.model

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("code") val code : String,
    @SerializedName("message") val message : String
)
