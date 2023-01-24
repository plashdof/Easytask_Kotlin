package com.week2.easytask.signup.model

import com.google.gson.annotations.SerializedName

data class SignupData(
    // T or F 로 입력
    @SerializedName("agreementCheck") val agreementCheck : String,
    @SerializedName("email") val email : String,
    @SerializedName("isApple") val isApple : Boolean,
    @SerializedName("isKakao") val isKakao : Boolean,
    // T or F 로 입력
    @SerializedName("marketing") val marketing : String,
    @SerializedName("password") val password : String,

    // 3 : 이루미
    // 4 : 고객
    @SerializedName("signupPurpose") val signupPurpose : String
)
