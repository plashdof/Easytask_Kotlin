package com.week2.easytask.login.model

import com.google.gson.annotations.SerializedName

data class SendEmailData(
    @SerializedName("email") val email : String
)
