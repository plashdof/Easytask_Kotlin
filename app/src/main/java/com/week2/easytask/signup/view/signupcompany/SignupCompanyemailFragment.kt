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
import com.week2.easytask.Retrofit
import com.week2.easytask.databinding.FragmentSignupcompanyEmailBinding
import com.week2.easytask.login.view.LoginActivity
import com.week2.easytask.signup.SignupSingleton
import com.week2.easytask.signup.network.ExistEmailAPI
import com.week2.easytask.signup.view.SignuppwFragment

class SignupCompanyemailFragment : Fragment() {


    private var _binding : FragmentSignupcompanyEmailBinding?= null
    private val binding get() = _binding!!

    private var email = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupcompanyEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        // edittext email 포커싱 이벤트 처리
        // -> 테두리색 변경
        // -> erase btn 보이게 / 안보이게
        // -> warning message 안보이게

        binding.etEmail.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
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

        // email edittext 입력 여부에 따라서, next btn 디자인 변경

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                email = binding.etEmail.text.toString()

                if(email.contains("@") && email.contains(".com")){
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

        binding.btnNext.setOnClickListener {

            SignupSingleton.email = email

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.frag_signup_company, SignuppwFragment())
                .addToBackStack(null)
                .commit()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}