package com.week2.easytask.signup.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.week2.easytask.R
import com.week2.easytask.Retrofit
import com.week2.easytask.databinding.FragmentSignupemailBinding
import com.week2.easytask.login.view.LoginActivity
import com.week2.easytask.signup.SignupSingleton
import com.week2.easytask.signup.model.ExistEmailResponse
import com.week2.easytask.signup.network.ExistEmailAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupemailFragment : Fragment() {


    private var _binding : FragmentSignupemailBinding? = null
    private val binding get() = _binding!!

    private val ExistemailRetro = Retrofit.getInstance().create(ExistEmailAPI::class.java)

    private var email = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupemailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // back btn 클릭 이벤트 처리
        // -> LoginActivity로 이동

        binding.btnBack.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        // banner ex btn 클릭 이벤트 처리
        // -> 배너 숨기기
        binding.btnBannerex.setOnClickListener {
            binding.layoutBanner.visibility = View.GONE
        }

        // banner btn 클릭 이벤트 처리
        // -> signup company activity 로 이동
        binding.btnMovecompany.setOnClickListener {
            val intent = Intent(requireContext(), SignupCompanyActivity::class.java)
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

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                email = binding.etEmail.text.toString()

                if(email.isNotBlank()){
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
        // -> /users/email/{}/exist API 호출

        binding.btnNext.setOnClickListener {
            ExistemailRetro
                .existemail(email)
                .enqueue(object : Callback<ExistEmailResponse> {
                    override fun onResponse(
                        call: Call<ExistEmailResponse>,
                        response: Response<ExistEmailResponse>
                    ) {
                        Log.d("API결과","${response.body()}")

                        if(response.body()!!.exists){
                            // 이미 존재하는 이메일이면, warn text 띄우기
                            
                            binding.tvWarn.visibility = View.VISIBLE
                        }else{
                            // 회원가입 안된 이메일이면, singleton 에 email 담고, pw frag 로 이동
                            SignupSingleton.email = email
                            
                            parentFragmentManager
                                .beginTransaction()
                                .replace(R.id.frag_signup,SignuppwFragment())
                                .commit()
                        }
                    }

                    override fun onFailure(call: Call<ExistEmailResponse>, t: Throwable) {}
                })

        }

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}