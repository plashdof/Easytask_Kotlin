package com.week2.easytask.login.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.week2.easytask.CustomToast.showpwChangeToast
import com.week2.easytask.webview.view.MainActivity
import com.week2.easytask.R
import com.week2.easytask.Retrofit
import com.week2.easytask.Singleton
import com.week2.easytask.databinding.ActivityLoginBinding
import com.week2.easytask.login.model.CheckKakaoResponse
import com.week2.easytask.login.model.SigninResponse
import com.week2.easytask.login.model.SigninData
import com.week2.easytask.login.network.CheckKakaoAPI
import com.week2.easytask.login.network.SigninAPI
import com.week2.easytask.signup.SignupSingleton
import com.week2.easytask.signup.view.SignupActivity
import com.week2.easytask.signup.view.SignupkakaoActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity:AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val SigninRetro = Retrofit.getloginInstance().create(SigninAPI::class.java)
    private val CheckKakaoRetro = Retrofit.getInstance().create(CheckKakaoAPI::class.java)

    private lateinit var sharedPreferences : SharedPreferences

    private var kakaoid = ""

    private var storestate = false
    private var pwstate = false
    private var Id = ""
    private var Pw = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("test", MODE_PRIVATE)

        // 아이디 불러오기
        loadID()

        // Id 찾기 성공후 login page 로 넘어오면, id 입력된상태로 login page 띄움

        if(intent.hasExtra("email")){
            Id = intent.getStringExtra("email").toString()
            binding.etId.setText(Id)
        }

        // pw 변경 성공후 login page 로 넘어오면, Toast message 띄움

        if(intent.hasExtra("pwchange")){
            Toast(this)
                .showpwChangeToast ("비밀번호를 변경했어요.", this)
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
                binding.btnShowPw.setImageResource(R.drawable.login_pw_icon)
                pwstate = false
            }else{
                binding.etPw.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.etPw.setSelection(Pw.length)
                binding.btnShowPw.setImageResource(R.drawable.login_pw_icon_on)
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
                    binding.btnLogin.isEnabled = true
                }else{
                    binding.btnLogin.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnLogin.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnLogin.isEnabled = false
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
                    binding.btnLogin.isEnabled= true
                }else{
                    binding.btnLogin.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnLogin.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnLogin.isEnabled = false
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })

        
        // 아이디 저장 checkbox 이벤트처리
        // 아이콘 변경

        binding.btnIdstore.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            if(storestate){
                binding.btnIdstore.setImageResource(R.drawable.login_checkoff)
                storestate = false

                editor.clear()
                editor.apply()

            }else{
                binding.btnIdstore.setImageResource(R.drawable.login_checkon)
                storestate = true

                editor.putString("storedID", Id)
                editor.apply()
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
            binding.btnLogin.isEnabled = false

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
                            Singleton.accessToken = response.body()!!.accessToken
                            Singleton.refreshToken = response.body()!!.refreshToken
                            Singleton.id = response.body()!!.id

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                            finish()

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


        // 로그인 정보 확인
//        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//            if (error != null) {
//                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
//            }
//            else if (tokenInfo != null) {
//                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
//                finish()
//            }
//        }



        // 카카오로그인 관련 에러 예외처리

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {

                // kakaotalk 에 연결 성공한뒤,
                
                UserApiClient.instance.me { user, error ->

                    Log.d("API결과","${user.toString()}")

                    // kakao userid 받아오기
                    kakaoid = user?.id.toString()
                    // 회원가입으로 이동시 필요한 email 정보 싱글톤에 저장
                    SignupSingleton.email = user?.kakaoAccount?.email.toString()

                }

                // kakao userid 로 easytask 엔드포인트 연결
                // -> 존재하는 회원의 kakao id 면 LoginAPI 호출
                // -> 존재하지 않는 회원의 kakao id 면 SignupKakaoActivity로
                CheckKakaoRetro
                    .checkkakao(kakaoid)
                    .enqueue(object : Callback<CheckKakaoResponse>{
                        override fun onResponse(
                            call: Call<CheckKakaoResponse>,
                            response: Response<CheckKakaoResponse>
                        ) {
                            Log.d("API결과","${response.raw()}")
                            if(response.code() == 200){
                                val datas = SigninData(SignupSingleton.email,"",true)
                                
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
                                                Singleton.accessToken = response.body()!!.accessToken
                                                Singleton.refreshToken = response.body()!!.refreshToken
                                                Singleton.id = response.body()!!.id

                                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                                finish()

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
                            }else{
                                val intent = Intent(this@LoginActivity, SignupkakaoActivity::class.java)
                                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                                finish()
                            }
                        }

                        override fun onFailure(call: Call<CheckKakaoResponse>, t: Throwable) {}
                    })


            }
        }
        
        
        // kakao 로그인 하기
        binding.btnKakaoLogin.setOnClickListener {
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
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

    fun kakaoLogout() {
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("Hello", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            } else {
                Log.i("Hello", "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    fun kakaoUnlink() {
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e("Hello", "연결 끊기 실패", error)
            } else {
                Log.i("Hello", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
        finish()
    }

    // 아이디 저장되어있는거 있으면 불러오기
    
    fun loadID(){
        val getSharedID = sharedPreferences.getString("storedID", "ERROR")

        if(getSharedID != "ERROR"){
            binding.etId.setText(getSharedID)
            Id = getSharedID.toString()

            binding.btnIdstore.setImageResource(R.drawable.login_checkon)
            storestate = true
        }
    }
}