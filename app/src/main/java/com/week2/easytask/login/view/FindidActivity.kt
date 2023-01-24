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
import com.week2.easytask.databinding.ActivityFindidBinding
import com.week2.easytask.login.model.FindemailResponse
import com.week2.easytask.login.network.FindemailAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindidActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFindidBinding
    private val FindRetro = Retrofit.getInstance().create(FindemailAPI::class.java)

    private var name = ""
    private var phone1 = ""
    private var phone2 = ""
    private var phone3 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindidBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // back btn 클릭 이벤트 처리

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        // name.phone 포커싱 이벤트 처리
        
        binding.etName.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
                binding.tvWarn.visibility = View.INVISIBLE
                binding.btnEraseName.visibility = View.VISIBLE
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
                binding.btnEraseName.visibility = View.INVISIBLE
            }
        }

        binding.etPhone1.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
                binding.tvWarn.visibility = View.INVISIBLE
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
            }
        }

        binding.etPhone2.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
                binding.tvWarn.visibility = View.INVISIBLE
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
            }
        }

        binding.etPhone3.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
                binding.tvWarn.visibility = View.INVISIBLE
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
            }
        }


        // name erase 버튼 클릭이벤트 처리

        binding.btnEraseName.setOnClickListener {
            name = ""
            binding.etName.setText("")
        }


        // edittext 상태 따라서 이메일 찾기 버튼 디자인 변경

        binding.etName.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                name = binding.etName.text.toString()

                if(name.isNotBlank() && phone1 == "010" && phone2.length == 4 && phone3.length == 4){
                    binding.btnFind.setBackgroundResource(R.drawable.shape_login_btn_on)
                    binding.btnFind.setTextColor(Color.parseColor("#FFFFFFFF"))
                    binding.btnFind.isEnabled = true
                }else{
                    binding.btnFind.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnFind.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnFind.isEnabled= false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etPhone1.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                phone1 = binding.etPhone1.text.toString()

                if(name.isNotBlank() && phone1 == "010" && phone2.length == 4 && phone3.length == 4){
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

        binding.etPhone2.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                phone2 = binding.etPhone2.text.toString()

                if(name.isNotBlank() && phone1 == "010" && phone2.length == 4 && phone3.length == 4){
                    binding.btnFind.setBackgroundResource(R.drawable.shape_login_btn_on)
                    binding.btnFind.setTextColor(Color.parseColor("#FFFFFFFF"))
                    binding.btnFind.isEnabled= true
                }else{
                    binding.btnFind.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnFind.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnFind.isEnabled = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etPhone3.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                phone3= binding.etPhone3.text.toString()

                if(name.isNotBlank() && phone1 == "010" && phone2.length == 4 && phone3.length == 4){
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


        // 이메일 찾기 버튼 클릭 이벤트 처리
        // -> FindemailAPI 통신
        // -> code 404 일시 warn 메세지 띄움
        // -> code 200 일시, FindidSuccessActivity 로 이동
        // -> 이동시 intent 에 email 담아서 보냄
        
        binding.btnFind.setOnClickListener {
            val phonenum = "$phone1-$phone2-$phone3"
            FindRetro
                .findemail(name,phonenum)
                .enqueue(object : Callback<FindemailResponse>{
                    override fun onResponse(
                        call: Call<FindemailResponse>,
                        response: Response<FindemailResponse>
                    ) {
                       Log.d("API결과", "${response.code()}")

                        // 404코드로 오면 회원정보 없음.
                        if(response.code() == 404){
                            binding.tvWarn.text = "일치하는 회원 정보가 없어요."
                            binding.tvWarn.visibility = View.VISIBLE

                        }else{
                            Log.d("API결과","${response.body()}")
                            val intent = Intent(this@FindidActivity,FindidSuccessActivity::class.java)
                                .putExtra("email","${response.body()?.email}")
                            startActivity(intent)
                        }


                    }

                    override fun onFailure(call: Call<FindemailResponse>, t: Throwable) {}
                })


        }


    }
}