package com.week2.easytask.login.model

import com.google.gson.annotations.SerializedName

data class FindpwResponse(
    @SerializedName("content") val content : ArrayList<Any>
)
