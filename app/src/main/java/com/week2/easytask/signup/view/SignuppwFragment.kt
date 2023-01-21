package com.week2.easytask.signup.view

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.week2.easytask.R
import com.week2.easytask.databinding.FragmentSignuppwBinding
import java.util.regex.Pattern

class SignuppwFragment : Fragment() {

    private var _binding : FragmentSignuppwBinding? = null
    private val binding get() = _binding!!

    private var pwstate = false
    private var pwCheckstate = false

    private var Pw = ""
    private var PwCheck = ""

    private var numcheck = false
    private var specialcheck =false
    private var lengthcheck =false
    private var equalcheck = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignuppwBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pw/Pwcheck 입력 edittext focus 이벤트 처리
        // -> 테두리 색변경

        binding.etPw.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
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
                pwstate = false
            }else{
                binding.etPw.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.etPw.setSelection(Pw.length)
                pwstate = true
            }
        }

        binding.btnShowPwCheck.setOnClickListener {
            if(pwCheckstate){
                binding.etPwCheck.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.etPwCheck.setSelection(PwCheck.length)
                pwCheckstate = false
            }else{
                binding.etPwCheck.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.etPwCheck.setSelection(PwCheck.length)
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
                    binding.btnNext.isClickable = true
                }else{
                    binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnNext.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnNext.isClickable = false
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
                    binding.btnNext.isClickable = true
                }else{
                    binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnNext.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnNext.isClickable = false
                }

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

        })


        // next btn 클릭 이벤트 처리
        // -> type 선택 페이지로 이동

        binding.btnNext.setOnClickListener {
            
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
    

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}