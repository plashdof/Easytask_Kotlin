package com.week2.easytask.login.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.week2.easytask.R
import com.week2.easytask.Retrofit
import com.week2.easytask.databinding.ActivityPwchangeBinding
import com.week2.easytask.login.model.PwchangeData
import com.week2.easytask.login.network.PwchangeAPI
import com.week2.easytask.signup.SignupSingleton
import com.week2.easytask.signup.view.SignuptypeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PwchangeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPwchangeBinding

    private val pwchangeRetro = Retrofit.getInstance().create(PwchangeAPI::class.java)

    private var pwstate = false
    private var pwCheckstate = false

    private var Pw = ""
    private var PwCheck = ""

    private var emailAuth = ""

    private var numcheck = false
    private var specialcheck =false
    private var lengthcheck =false
    private var equalcheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPwchangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        // Pw/Pwcheck 입력 edittext focus 이벤트 처리
        // -> 테두리 색변경

        binding.etPw.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.layoutCheckpw.visibility = View.VISIBLE
                binding.tvWarn.visibility = View.GONE
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)

                // todo 포커스 해제시 API를 통해서, 기존비번과 같은지 판별후 경고메세지 띄우기. (API 확인불가)


            }
        }

        binding.etPwCheck.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
            }
        }

        // pw / pwcheck input icon 이벤트 처리
        // 클릭시 비번 보임

        binding.btnShowPw.setOnClickListener {
            if(pwstate){
                binding.etPw.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.etPw.setSelection(Pw.length)
                binding.btnShowPw.setImageResource(R.drawable.login_pw_icon)
                pwstate = false
            }else{
                binding.etPw.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.etPw.setSelection(Pw.length)
                binding.btnShowPw.setImageResource(R.drawable.login_pw_icon_on)
                pwstate = true
            }
        }

        binding.btnShowPwCheck.setOnClickListener {
            if(pwCheckstate){
                binding.etPwCheck.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.etPwCheck.setSelection(PwCheck.length)
                binding.btnShowPwCheck.setImageResource(R.drawable.login_pw_icon)
                pwCheckstate = false
            }else{
                binding.etPwCheck.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.etPwCheck.setSelection(PwCheck.length)
                binding.btnShowPwCheck.setImageResource(R.drawable.login_pw_icon_on)
                pwCheckstate = true
            }
        }

        // pw edit text 입력에 따른 변화 처리
        // -> 길이검사
        // -> 숫자포함 겸사
        // -> 특수문자 포함 검사

        binding.etPw.addTextChangedListener(object: TextWatcher {


            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Pw = binding.etPw.text.toString()


                // 비밀번호 8자이상 16자이하 조건검사
                if(Pw.length in 8..16){
                    binding.ivPwlength.setImageResource(R.drawable.signup_pwcheck_on)
                    binding.tvPwlength.setTextColor(Color.parseColor("#4886EB"))
                    lengthcheck = true
                }else{
                    binding.ivPwlength.setImageResource(R.drawable.signup_pwcheck)
                    binding.tvPwlength.setTextColor(Color.parseColor("#A2A9B0"))
                    lengthcheck = false
                }

                // 비밀번호 숫자포함 조건검사

                if(findnum()){
                    binding.ivPwnum.setImageResource(R.drawable.signup_pwcheck_on)
                    binding.tvPwnum.setTextColor(Color.parseColor("#4886EB"))

                    numcheck = true
                }else{
                    binding.ivPwnum.setImageResource(R.drawable.signup_pwcheck)
                    binding.tvPwnum.setTextColor(Color.parseColor("#A2A9B0"))

                    numcheck = false
                }


                // 비밀번호 특수문자 포함 조건검사
                if(findspecial()){
                    binding.ivPwspecial.setImageResource(R.drawable.signup_pwcheck_on)
                    binding.tvPwspecial.setTextColor(Color.parseColor("#4886EB"))

                    specialcheck = true
                }else{
                    binding.ivPwspecial.setImageResource(R.drawable.signup_pwcheck)
                    binding.tvPwspecial.setTextColor(Color.parseColor("#A2A9B0"))

                    specialcheck = false
                }

                // 모든조건 달성시 next btn 활성화

                if(numcheck && specialcheck && lengthcheck && equalcheck){
                    binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn_on)
                    binding.btnNext.setTextColor(Color.parseColor("#FFFFFFFF"))
                    binding.btnNext.isEnabled = true
                }else{
                    binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnNext.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnNext.isEnabled = false
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })

        // pwcheck edit text 입력에 따른 변화 처리
        // -> 일치여부 검사


        binding.etPwCheck.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                PwCheck = binding.etPwCheck.text.toString()


                // 비밀번호 일치 조건검사
                if(Pw == PwCheck && PwCheck.isNotBlank()){
                    binding.ivPwcheck.setImageResource(R.drawable.signup_pwcheck_on)
                    binding.tvPwcheck.setTextColor(Color.parseColor("#4886EB"))

                    equalcheck = true
                }else{
                    binding.ivPwcheck.setImageResource(R.drawable.signup_pwcheck)
                    binding.tvPwcheck.setTextColor(Color.parseColor("#A2A9B0"))

                    equalcheck = false
                }


                // 모든조건 달성시 next btn 활성화

                if(numcheck && specialcheck && lengthcheck && equalcheck){
                    binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn_on)
                    binding.btnNext.setTextColor(Color.parseColor("#FFFFFFFF"))
                    binding.btnNext.isEnabled = true
                }else{
                    binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnNext.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnNext.isEnabled = false
                }

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })


        // next btn 클릭 이벤트 처리
        // -> type 선택 페이지로 이동

        binding.btnNext.setOnClickListener {
            SignupSingleton.pw = Pw


            // 기업모드일 경우
            if(SignupSingleton.state == "company"){

            }else if(SignupSingleton.state == "normal"){

                // 일반모드일 경우

                val datas = PwchangeData(emailAuth,Pw)
                pwchangeRetro.pwchange(datas)
                    .enqueue(object: Callback<PwchangeData>{
                        override fun onResponse(
                            call: Call<PwchangeData>,
                            response: Response<PwchangeData>
                        ) {
                            if(response.code() == 200){
                                val intent = Intent(this@PwchangeActivity, LoginActivity::class.java)
                                    .putExtra("pwchange","success")
                                startActivity(intent)
                            }
                        }

                        override fun onFailure(call: Call<PwchangeData>, t: Throwable) {}
                    })

            }

        }



    }

    // 숫자포함 조건검사 함수

    fun findnum() : Boolean{

        var result = false
        for(i in Pw){
            if(i - '0' in 0..9){
                result = true
            }
        }

        return result
    }

    // 특수문자 포함 조건검사 함수

    fun findspecial() : Boolean{

        var result = false

        for(i in Pw){
            if(i in "?/~!@#\$%^&*()+=\\"){
                result = true
            }
        }
        return result
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