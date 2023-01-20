package com.week2.easytask.login.model

import com.google.gson.annotations.SerializedName

data class SigninResponse(
    @SerializedName("id") val id : String,
    @SerializedName("accessToken") val accessToken : String
)
