package com.week2.easytask.webview.model

import com.google.gson.annotations.SerializedName

data class GetaccessTokenData(
    @SerializedName("userId") val userId : Int,
    @SerializedName("refreshToken") val refreshToken : String
)
