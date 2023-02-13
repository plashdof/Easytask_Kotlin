package com.week2.easytask.webview.model

import com.google.gson.annotations.SerializedName

data class AfterSigninResponse(
    @SerializedName("roleList") val roleList : ArrayList<datas>
)

data class datas(
    @SerializedName("description") val description : String
)
