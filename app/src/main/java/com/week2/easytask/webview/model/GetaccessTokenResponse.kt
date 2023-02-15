package com.week2.easytask.webview.model

import com.google.gson.annotations.SerializedName

data class GetaccessTokenResponse(
    @SerializedName("accessToken") val accessToken : String,
    @SerializedName("refreshToken") val refreshToken : String
)
