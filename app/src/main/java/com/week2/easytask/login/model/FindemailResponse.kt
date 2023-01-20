package com.week2.easytask.login.model

import com.google.gson.annotations.SerializedName

data class FindemailResponse(
    @SerializedName("email") val email : String
)
