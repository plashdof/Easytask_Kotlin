package com.week2.easytask.signup.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.week2.easytask.R
import com.week2.easytask.databinding.BottomsheetSignupBinding
import com.week2.easytask.signup.SignupSingleton

class BottomSheetSignup : BottomSheetDialogFragment() {

    private var _binding : BottomsheetSignupBinding? =null
    private val binding get() = _binding!!

    private var terms1state = false
    private var terms2state = false
    private var terms3state = false
    private var alltermsstate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomsheetSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // 약관 동의 체크박스 버튼 클릭이벤트 처리

        binding.btnTerms1.setOnClickListener {
            if(terms1state){
                binding.btnTerms1.setImageResource(R.drawable.signup_checkoff)
                terms1state = false

                // 셋중 하나라도 체크 안되었다면, 모두동의 체크 풀기
                if(!terms1state || !terms2state || !terms3state){
                    binding.btnAllterms.setImageResource(R.drawable.signup_checkoff)
                    alltermsstate = false
                }

            }else{
                binding.btnTerms1.setImageResource(R.drawable.signup_checkon)
                terms1state = true


                // 세가지 모두 체크되었다면, 모두동의 자동체크로
                if(terms1state && terms2state && terms3state){
                    binding.btnAllterms.setImageResource(R.drawable.signup_checkon)
                    alltermsstate = true
                }
            }
            
            // 필수약관 동의시에만 다음버튼 활성화
            
            if(terms1state && terms2state){
                binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn_on)
                binding.btnNext.setTextColor(Color.parseColor("#FFFFFFFF"))
                binding.btnNext.isEnabled = true
            }else{
                binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn)
                binding.btnNext.setTextColor(Color.parseColor("#D3D7DC"))
                binding.btnNext.isEnabled = false
            }
        }

        binding.btnTerms2.setOnClickListener {
            if(terms2state){
                binding.btnTerms2.setImageResource(R.drawable.signup_checkoff)
                terms2state = false

                // 셋중 하나라도 체크 안되었다면, 모두동의 체크 풀기
                if(!terms1state || !terms2state || !terms3state){
                    binding.btnAllterms.setImageResource(R.drawable.signup_checkoff)
                    alltermsstate = false
                }

            }else{
                binding.btnTerms2.setImageResource(R.drawable.signup_checkon)
                terms2state = true

                // 세가지 모두 체크되었다면, 모두동의 체크
                if(terms1state && terms2state && terms3state){
                    binding.btnAllterms.setImageResource(R.drawable.signup_checkon)
                    alltermsstate = true
                }
            }

            // 필수약관 동의시에만 다음버튼 활성화

            if(terms1state && terms2state){
                binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn_on)
                binding.btnNext.setTextColor(Color.parseColor("#FFFFFFFF"))
                binding.btnNext.isEnabled = true
            }else{
                binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn)
                binding.btnNext.setTextColor(Color.parseColor("#D3D7DC"))
                binding.btnNext.isEnabled = false
            }
        }

        binding.btnTerms3.setOnClickListener {
            if(terms3state){
                binding.btnTerms3.setImageResource(R.drawable.signup_checkoff)
                terms3state = false

                // 셋중 하나라도 체크 안되었다면, 모두동의 체크 풀기
                if(!terms1state || !terms2state || !terms3state){
                    binding.btnAllterms.setImageResource(R.drawable.signup_checkoff)
                    alltermsstate = false
                }

            }else{
                binding.btnTerms3.setImageResource(R.drawable.signup_checkon)
                terms3state = true

                // 세가지 모두 체크되었다면, 모두동의 자동체크로
                if(terms1state && terms2state && terms3state){
                    binding.btnAllterms.setImageResource(R.drawable.signup_checkon)
                    alltermsstate = true
                }
            }

            // 필수약관 동의시에만 다음버튼 활성화

            if(terms1state && terms2state){
                binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn_on)
                binding.btnNext.setTextColor(Color.parseColor("#FFFFFFFF"))
                binding.btnNext.isEnabled = true
            }else{
                binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn)
                binding.btnNext.setTextColor(Color.parseColor("#D3D7DC"))
                binding.btnNext.isEnabled = false
            }
        }

        // 약관 모두동의 버튼 클릭이벤트 처리

        binding.btnAllterms.setOnClickListener {
            if(alltermsstate){

                binding.btnTerms1.setImageResource(R.drawable.signup_checkoff)
                binding.btnTerms2.setImageResource(R.drawable.signup_checkoff)
                binding.btnTerms3.setImageResource(R.drawable.signup_checkoff)
                terms1state = false
                terms2state = false
                terms3state = false

                binding.btnAllterms.setImageResource(R.drawable.signup_checkoff)
                alltermsstate = false

            }else{
                binding.btnTerms1.setImageResource(R.drawable.signup_checkon)
                binding.btnTerms2.setImageResource(R.drawable.signup_checkon)
                binding.btnTerms3.setImageResource(R.drawable.signup_checkon)
                terms1state = true
                terms2state = true
                terms3state = true

                binding.btnAllterms.setImageResource(R.drawable.signup_checkon)
                alltermsstate = true

            }


            // 필수약관 동의시에만 다음버튼 활성화

            if(terms1state && terms2state){
                binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn_on)
                binding.btnNext.setTextColor(Color.parseColor("#FFFFFFFF"))
                binding.btnNext.isClickable = true
            }else{
                binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn)
                binding.btnNext.setTextColor(Color.parseColor("#D3D7DC"))
                binding.btnNext.isClickable = false
            }
        }


        // 다음버튼 클릭이벤트 처리

        binding.btnNext.setOnClickListener {
            SignupSingleton.agreementCheck = "T"

            if(terms3state){
                SignupSingleton.marketing = "T"
            }else{
                SignupSingleton.marketing = "F"
            }

            // 기업모드일 경우
            if(SignupSingleton.company){
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.frag_signup_company,SignupcompleteFragment())
                    .addToBackStack(null)
                    .commit()
            }else{
                // 일반모드일 경우
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.frag_signup,SignupcompleteFragment())
                    .addToBackStack(null)
                    .commit()
            }

        }
        
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}