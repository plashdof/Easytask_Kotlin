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
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.R
import com.week2.easytask.Retrofit
import com.week2.easytask.databinding.ActivityLoginBinding
import com.week2.easytask.login.model.SigninResponse
import com.week2.easytask.login.model.SigninData
import com.week2.easytask.login.network.SigninAPI
import com.week2.easytask.signup.view.SignupActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity:AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val SigninRetro = Retrofit.getInstance().create(SigninAPI::class.java)

    private var storestate = false
    private var pwstate = false
    private var Id = ""
    private var Pw = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Id 찾기 성공후 login page 로 넘어오면, id 입력된상태로 login page 띄움

        if(intent.hasExtra("email")){
            Id = intent.getStringExtra("email").toString()
            binding.etId.setText(Id)
        }

        
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
        // -> SigninAPI 통신
        // -> response code 200 일시 로그인성공. 추후 홈으로 이동
        // -> response code 401 일시 없는 아이디
        // -> login btn, 경고문구 디자인 변경
        
        binding.btnLogin.setOnClickListener {

            // API 통신전
            binding.btnLogin.setTextColor(Color.parseColor("#93C3F8"))
            binding.etPw.clearFocus()
            binding.etId.clearFocus()
            binding.btnLogin.isClickable = false

            val datas = SigninData(username = Id, password = Pw, isKakao = false)

            SigninRetro
                .signin(datas)
                .enqueue(object : Callback<SigninResponse>{
                    override fun onResponse(
                        call: Call<SigninResponse>,
                        response: Response<SigninResponse>
                    ) {
                        Log.d("API결과","${response.code()}")

                        if(response.code() == 200){
                            Log.d("API결과","${response.body()}")

                        }else if(response.code() == 401){
                            // API 통신 후  
                            // 버튼색. 버튼글자색 변경
                            // edittext focus 해제
                            // 경고문구 visible 처리

                            binding.tvLoginFail.visibility = View.VISIBLE
                            binding.btnLogin.setBackgroundResource(R.drawable.shape_login_btn)
                            binding.btnLogin.setTextColor(Color.parseColor("#D3D7DC"))
                        }

                    }
                    override fun onFailure(call: Call<SigninResponse>, t: Throwable) {
                        Log.d("API결과","${t.message}")
                    }
                })
        }

        // 아이디 찾기 
        binding.tvFindId.setOnClickListener {
            val intent = Intent(this, FindidActivity::class.java)
            startActivity(intent)
        }

        // 비번 찾기
        binding.tvFindPw.setOnClickListener {
            val intent = Intent(this,FindpwActivity::class.java)
            startActivity(intent)
        }

        // 회원가입 하기
        binding.tvSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
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