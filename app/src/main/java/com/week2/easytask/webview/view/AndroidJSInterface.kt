package com.week2.easytask.webview.view

import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView

object AndroidJSInterface {

    @JavascriptInterface
    fun  logoutCallNativeApp () {
        Log.d("API결과", "logout button clicked" )
    }


}

