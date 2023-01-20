package com.week2.easytask.login.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.R
import com.week2.easytask.databinding.ActivityFindpwBinding

class FindpwActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFindpwBinding

    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindpwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // back btn 클릭 이벤트 처리

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        // edittext email 포커싱 이벤트 처리

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
                    binding.btnFind.isClickable = true
                }else{
                    binding.btnFind.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnFind.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnFind.isClickable = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })


        // find btn 클릭시 이벤트 처리

        binding.btnFind.setOnClickListener {

        }
    }
}