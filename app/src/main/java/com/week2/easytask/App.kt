package com.week2.easytask

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk

class App : Application() {

    init{
        instance=this
    }


    companion object{
        private var instance : App? = null

        // 앱의 context 를 불러오는 함수
        fun getcontext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "7fbc7eaf88afabd9aed86047058df17f")
    }
}