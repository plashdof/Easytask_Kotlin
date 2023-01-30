package com.week2.easytask.signup.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.week2.easytask.MainActivity
import com.week2.easytask.Singleton
import com.week2.easytask.Retrofit
import com.week2.easytask.databinding.FragmentSignupcompleteBinding
import com.week2.easytask.login.model.SigninResponse
import com.week2.easytask.signup.SignupSingleton
import com.week2.easytask.signup.model.SignupData
import com.week2.easytask.signup.network.SignupAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupcompleteFragment : Fragment() {

    private var _binding : FragmentSignupcompleteBinding? = null
    private val binding get() = _binding!!

    private var SignupRetro = Retrofit.getInstance().create(SignupAPI::class.java)

    // signup singleton 에 담긴 모든 signup 데이터 패키징
    val datas = SignupData(SignupSingleton.agreementCheck,
        SignupSingleton.email,
        SignupSingleton.isApple,
        SignupSingleton.isKakao,
        SignupSingleton.marketing,
        SignupSingleton.pw,
        SignupSingleton.signupPurpose)   

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignupcompleteBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // /signup API 통신
        // 통신후 token 저장

        Log.d("API결과","${datas.toString()}")
        SignupRetro
            .signup(datas)
            .enqueue(object : Callback<SigninResponse>{
                override fun onResponse(
                    call: Call<SigninResponse>,
                    response: Response<SigninResponse>
                ) {
                    Log.d("API결과","${response.raw()}")

                    if(response.code() == 200){
                        Singleton.accessToken = response.body()!!.accessToken
                        Singleton.refreshToken = response.body()!!.refreshToken
                        Singleton.id = response.body()!!.id

                        binding.btnStart.isEnabled = true
                    }else{
                        Log.d("API결과", "error")
                    }    

                }

                override fun onFailure(call: Call<SigninResponse>, t: Throwable) {}
            })


        // 시작하기 버튼 클릭 이벤트 처리
        binding.btnStart.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}