package com.week2.easytask.signup.view.signupcompany

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.week2.easytask.R
import com.week2.easytask.databinding.FragmentSignupcompanyNameBinding
import com.week2.easytask.login.view.LoginActivity
import com.week2.easytask.signup.SignupSingleton

class SignupCompanynameFragment : Fragment() {

    private var _binding : FragmentSignupcompanyNameBinding? = null
    private val binding get() = _binding!!

    private var companyName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupcompanyNameBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SignupSingleton 회사모드로
        SignupSingleton.company = true


        // back btn 클릭 이벤트 처리
        // -> LoginActivity로 이동

        binding.btnBack.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }


        // edittext company name 포커싱 이벤트 처리
        // -> 테두리색 변경
        // -> erase btn 보이게 / 안보이게
        // -> warning message 안보이게

        binding.etCompanyName.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
                binding.tvWarn.visibility = View.INVISIBLE
                binding.btnEraseName.visibility = View.VISIBLE
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
                binding.btnEraseName.visibility = View.INVISIBLE
            }
        }

        // company name erase 버튼 클릭이벤트 처리

        binding.btnEraseName.setOnClickListener {
            companyName = ""
            binding.etCompanyName.setText("")
        }

        // company name edittext 입력 여부에 따라서, next btn 디자인 변경

        binding.etCompanyName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                companyName = binding.etCompanyName.text.toString()

                if(companyName.isNotBlank()){
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


        // 다음버튼 클릭 이벤트 처리

        binding.btnNext.setOnClickListener {

            // Singleton 에 company name 저장
            SignupSingleton.companyName = companyName

            // companynum frag 로 이동
            parentFragmentManager.beginTransaction()
                .replace(R.id.frag_signup_company, SignupCompanynumFragment())
                .commit()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}