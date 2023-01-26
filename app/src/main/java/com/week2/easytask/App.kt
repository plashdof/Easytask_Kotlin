package com.week2.easytask

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "7fbc7eaf88afabd9aed86047058df17f")
    }
}