package com.week2.easytask.signup.view.signupcompany

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.R
import com.week2.easytask.databinding.ActivitySingupcompanyBinding
import com.week2.easytask.signup.SignupSingleton

class SignupCompanyActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySingupcompanyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingupcompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // name frag 부터 시작
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_signup_company, SignupCompanynameFragment())
            .commit()

    }

    // 다른곳 클릭시 키보드 내리기 & edit text focus 해제
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action === MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}