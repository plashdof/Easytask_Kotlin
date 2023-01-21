package com.week2.easytask.signup.model

import com.google.gson.annotations.SerializedName

data class ExistEmailResponse(
    @SerializedName("exists") val exists : Boolean
)
