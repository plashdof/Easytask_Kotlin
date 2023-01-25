package com.week2.easytask.signup.model

import com.google.gson.annotations.SerializedName

data class ExistCompanynumResponse(
    @SerializedName("exists") val exists : Boolean,
    @SerializedName("companyId") val companyId : String
)
