package com.week2.easytask

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.databinding.ActivityLoginBinding

class LoginActivity:AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private var storestate = false
    private var pwstate = false
    private var Id = ""
    private var Pw = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        
        // Id/Pw 입력 edittext focus 이벤트 처리
        // 경고문구 invisible 처리
        // 테두리 색변경
        
        binding.etId.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
                binding.tvLoginFail.visibility = View.INVISIBLE
                binding.btnEraseId.visibility = View.VISIBLE
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
                binding.btnEraseId.visibility = View.INVISIBLE
            }
        }

        binding.etPw.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
                binding.tvLoginFail.visibility = View.INVISIBLE
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
            }
        }

        // id input icon & pw input icon 이벤트 처리
        // 클릭시 문구 다 사라짐
        // 클릭시 비번 보임

        binding.btnEraseId.setOnClickListener {
            Id = ""
            binding.etId.setText("")
        }

        binding.btnShowPw.setOnClickListener {
            if(pwstate){
                binding.etPw.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.etPw.setSelection(Pw.length)
                pwstate = false
            }else{
                binding.etPw.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.etPw.setSelection(Pw.length)
                pwstate = true
            }

        }


        // Id/Pw 입력 edittext 입력 String 이벤트 처리
        // 둘다 입력되었을때만 로그인버튼 활성화 / 배경색 변경 / 글자색 변경

        binding.etId.addTextChangedListener(object:TextWatcher{

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Id = binding.etId.text.toString()

                if(Id.isNotBlank() && Pw.isNotBlank()){
                    binding.btnLogin.setBackgroundResource(R.drawable.shape_login_btn_on)
                    binding.btnLogin.setTextColor(Color.parseColor("#FFFFFFFF"))
                    binding.btnLogin.isClickable = true
                }else{
                    binding.btnLogin.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnLogin.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnLogin.isClickable = false
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })

        binding.etPw.addTextChangedListener(object:TextWatcher{


            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Pw = binding.etPw.text.toString()

                if(Id.isNotBlank() && Pw.isNotBlank()){
                    binding.btnLogin.setBackgroundResource(R.drawable.shape_login_btn_on)
                    binding.btnLogin.setTextColor(Color.parseColor("#FFFFFFFF"))
                    binding.btnLogin.isClickable = true
                }else{
                    binding.btnLogin.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnLogin.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnLogin.isClickable = false
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })

        
        // 아이디 저장 checkbox 이벤트처리
        // 아이콘 변경

        binding.btnIdstore.setOnClickListener {
            if(storestate){
                binding.btnIdstore.setImageResource(R.drawable.login_checkoff)
                storestate = false
            }else{
                binding.btnIdstore.setImageResource(R.drawable.login_checkon)
                storestate = true
            }
        }

        // 로그인버튼 클릭 이벤트처리
        // 버튼색. 버튼글자색 변경
        // edittext focus 해제
        // 경고문구 visible 처리
        binding.btnLogin.setOnClickListener {
            binding.tvLoginFail.visibility = View.VISIBLE

            binding.btnLogin.setBackgroundResource(R.drawable.shape_login_btn)
            binding.btnLogin.setTextColor(Color.parseColor("#D3D7DC"))
            binding.btnLogin.isClickable = false

            binding.etPw.clearFocus()
            binding.etId.clearFocus()
        }


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