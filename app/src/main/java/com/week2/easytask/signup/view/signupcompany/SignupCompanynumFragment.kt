package com.week2.easytask.signup.view.signupcompany

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.week2.easytask.App
import com.week2.easytask.CustomToast.showSignupCompanyToast
import com.week2.easytask.R
import com.week2.easytask.Retrofit
import com.week2.easytask.databinding.FragmentSignupcompanyNumBinding
import com.week2.easytask.login.view.LoginActivity
import com.week2.easytask.signup.SignupSingleton
import com.week2.easytask.signup.model.CheckCompanynumData
import com.week2.easytask.signup.model.CheckCompanynumResponse
import com.week2.easytask.signup.model.ExistCompanynumResponse
import com.week2.easytask.signup.network.CheckCompanynumAPI
import com.week2.easytask.signup.network.ExistCompanynumAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupCompanynumFragment : Fragment() {

    private var _binding : FragmentSignupcompanyNumBinding? = null
    private val binding get() = _binding!!

    private val ExistCompanynumRetro = Retrofit.getInstance().create(ExistCompanynumAPI::class.java)
    private val CheckCompanynumRetro = Retrofit.getcnInstance().create(CheckCompanynumAPI::class.java)


    private val serviceKey = "hygOHoAe6VP7NVmNEbdh8ddWDzIlGxsgLb7NMaousvCCuCJlBzptb5KrlMEqXwuZJ5Q6EepdDtwGHWjxu3nI8w=="
    private val resultType = "JSON"

    val inputMethodManager = App.getcontext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    private var firstnum = ""
    private var secondnum = ""
    private var thirdnum = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupcompanyNumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        // 사업자 등록 번호 입력칸 3개 포커싱 이벤트 처리

        binding.etFirstnum.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
                binding.tvWarn.visibility = View.INVISIBLE
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
            }
        }

        binding.etSecondnum.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
                binding.tvWarn.visibility = View.INVISIBLE
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
            }
        }

        binding.etThirdnum.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
                binding.tvWarn.visibility = View.INVISIBLE
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
            }
        }

        // 인증하기 버튼 활성화 여부

        binding.etFirstnum.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                firstnum = binding.etFirstnum.text.toString()

                //
                if(firstnum.length ==3){
                    binding.etSecondnum.requestFocus()
                    binding.etSecondnum.setText("")
                }

                if(firstnum.length == 3 && secondnum.length == 2 && thirdnum.length == 5){
                    binding.btnCheck.setBackgroundResource(R.drawable.shape_login_btn_on)
                    binding.btnCheck.setTextColor(Color.parseColor("#FFFFFFFF"))
                    binding.btnCheck.isEnabled = true
                }else{
                    binding.btnCheck.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnCheck.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnCheck.isEnabled = false
                }
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })


        binding.etSecondnum.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                secondnum = binding.etSecondnum.text.toString()

                if(secondnum.length == 2){
                    binding.etThirdnum.requestFocus()
                    binding.etThirdnum.setText("")
                }

                if(firstnum.length == 3 && secondnum.length == 2 && thirdnum.length == 5){
                    binding.btnCheck.setBackgroundResource(R.drawable.shape_login_btn_on)
                    binding.btnCheck.setTextColor(Color.parseColor("#FFFFFFFF"))
                    binding.btnCheck.isEnabled = true
                }else{
                    binding.btnCheck.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnCheck.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnCheck.isEnabled = false
                }

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etThirdnum.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                thirdnum = binding.etThirdnum.text.toString()

                if(thirdnum.length == 5){
                    binding.etThirdnum.clearFocus()
                    inputMethodManager.hideSoftInputFromWindow(binding.etThirdnum.windowToken, 0)
                }

                if(firstnum.length == 3 && secondnum.length == 2 && thirdnum.length == 5){
                    binding.btnCheck.setBackgroundResource(R.drawable.shape_login_btn_on)
                    binding.btnCheck.setTextColor(Color.parseColor("#FFFFFFFF"))
                    binding.btnCheck.isEnabled = true
                }else{
                    binding.btnCheck.setBackgroundResource(R.drawable.shape_login_btn)
                    binding.btnCheck.setTextColor(Color.parseColor("#D3D7DC"))
                    binding.btnCheck.isEnabled = false
                }

            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })



        // 인증하기 버튼 클릭시 이벤트처리

        binding.btnCheck.setOnClickListener {

            ExistCompanynumRetro
                .existcompanynum(firstnum + secondnum + thirdnum)
                .enqueue(object : Callback<ExistCompanynumResponse>{
                    override fun onResponse(
                        call: Call<ExistCompanynumResponse>,
                        response: Response<ExistCompanynumResponse>
                    ) {
                        Log.d("API결과", "${response.body()}")

                        SignupSingleton.companyNum = firstnum + secondnum + thirdnum

                        if(response.code() == 200){
                            if(response.body()!!.exists){
                                // 이미 가입된 기업회원이면 경고문구 띄우기
                                binding.tvWarn.visibility = View.VISIBLE
                                binding.tvWarn.setText("이미 가입된 기업회원입니다")
                                
                            }else{
                                CheckCompanynum()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ExistCompanynumResponse>, t: Throwable) {}
                })
        }
    }

    fun CheckCompanynum(){
        val data = arrayListOf<String>(SignupSingleton.companyNum)
        val datas = CheckCompanynumData(data)

        Log.d("API결과","${datas}")

        CheckCompanynumRetro
            .checkcompanynum(serviceKey,resultType,datas)
            .enqueue(object : Callback<CheckCompanynumResponse>{
                override fun onResponse(
                    call: Call<CheckCompanynumResponse>,
                    response: Response<CheckCompanynumResponse>
                ) {
                    Log.d("API결과", "${response.body()}")

                    SignupSingleton.companyNum = firstnum + secondnum + thirdnum

                    if(response.code() == 200){
                        if(response.body()?.match_cnt == 1){
                            Toast(requireContext()).showSignupCompanyToast ("사업자 등록번호 인증에 성공했어요.", requireActivity())
                            parentFragmentManager.beginTransaction()
                                .replace(R.id.frag_signup_company, SignupCompanyemailFragment())
                                .addToBackStack(null)
                                .commit()
                        }else{
                            binding.tvWarn.visibility = View.VISIBLE
                            binding.tvWarn.setText("법인 사업자만 가입 가능합니다")
                        }
                    }


                }

                override fun onFailure(call: Call<CheckCompanynumResponse>, t: Throwable) {
                }
            })
    }






}