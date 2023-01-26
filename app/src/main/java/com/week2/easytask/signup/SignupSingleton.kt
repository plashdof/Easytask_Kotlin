package com.week2.easytask.signup

import com.google.gson.annotations.SerializedName

object SignupSingleton {

    var state = "normal"

    var companyName = ""
    var companyNum = ""

    var isKakao = false
    var isApple = false

    var email = ""
    var pw = ""
    var signupPurpose = ""
    var marketing = ""
    var agreementCheck = ""
}