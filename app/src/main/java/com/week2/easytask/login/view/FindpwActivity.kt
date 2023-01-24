package com.week2.easytask.login.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.R
import com.week2.easytask.Retrofit
import com.week2.easytask.databinding.ActivityFindpwBinding
import com.week2.easytask.login.model.FindpwResponse
import com.week2.easytask.login.model.SendEmailData
import com.week2.easytask.login.network.FindpwAPI
import com.week2.easytask.login.network.SendEmailAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindpwActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFindpwBinding
    private val FindpwRetro = Retrofit.getInstance().create(FindpwAPI::class.java)
    private val SendemailRetro = Retrofit.getInstance().create(SendEmailAPI::class.java)

    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindpwBinding.inflate(layoutInflater)
        setContentView(binding.root)
        

        // back btn 클릭 이벤트 처리
        // -> LoginActivity로 이동

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        // edittext email 포커싱 이벤트 처리
        // -> 테두리색 변경
        // -> erase btn 보이게 / 안보이게
        // -> warning message 안보이게
        
        binding.etEmail.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
                binding.tvWarn.visibility = View.INVISIBLE
                binding.btnEraseEmail.visibility = View.VISIBLE
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
                binding.btnEraseEmail.visibility = View.INVISIBLE
            }
        }

        // email erase 버튼 클릭이벤트 처리

        binding.btnEraseEmail.setOnClickListener {
            email = ""
            binding.etEmail.setText("")
        }


        // email edittext 입력 여부에 따라서, find btn 디자인 변경

        binding.etEmail.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                email = binding.etEmail.text.toString()

                if(email.isNotBlank()){
                    binding.btnFind.setBackgroundResource(R.drawable.shape_login_btn_on)
                    binding.btnFind.setTextColor(Color.parseColor("#FFFFFFFF"))
                    binding.btnFind.isEnabled = true
                }else{
                    binding.btnFind.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnFind.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnFind.isEnabled = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })


        // find btn 클릭시 이벤트 처리
        // -> FindpwAPI 호출
        // -> 성공시 SendEmailAPI 호출
        // -> 성공시 FindpwSuccessActivity 로 이동
        // -> 이동시 intent 에 email값 담아서 이동

        binding.btnFind.setOnClickListener {

            FindpwRetro
                .findpw(email)
                .enqueue(object : Callback<FindpwResponse>{
                    override fun onResponse(
                        call: Call<FindpwResponse>,
                        response: Response<FindpwResponse>
                    ) {
                        Log.d("API결과","${response.body()?.content}")

                        // 일치하는 계정이 없을경우
                        if(response.body()?.content!!.isEmpty()){
                            binding.tvWarn.visibility = View.VISIBLE
                            
                        }else{
                            
                            // 일치하는 계정이 있을경우, 해당 이메일로 이메일 전송 API!

                            val datas = SendEmailData(email)
                            SendemailRetro.sendeamil(datas)
                                .enqueue(object : Callback<SendEmailData>{
                                    override fun onResponse(
                                        call: Call<SendEmailData>,
                                        response: Response<SendEmailData>
                                    ) {
                                        if(response.code() == 200){
                                            val intent = Intent(this@FindpwActivity, FindpwSuccessActivity::class.java )
                                                .putExtra("email",email)
                                            startActivity(intent)
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<SendEmailData>,
                                        t: Throwable
                                    ) {}
                                })


                        }
                    }

                    override fun onFailure(call: Call<FindpwResponse>, t: Throwable) {}
                })
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