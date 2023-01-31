package com.week2.easytask.login.model

import com.google.gson.annotations.SerializedName

data class PwchangeData(
    @SerializedName("emailAuthCode") val emailAuthCode : String,
    @SerializedName("newPassword") val newPassword : String
)
