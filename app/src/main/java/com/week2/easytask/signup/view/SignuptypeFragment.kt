package com.week2.easytask.signup.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.week2.easytask.R
import com.week2.easytask.databinding.FragmentSignuptypeBinding
import com.week2.easytask.signup.SignupSingleton

class SignuptypeFragment : Fragment() {

    private var _binding : FragmentSignuptypeBinding? = null
    private val binding get() = _binding!!

    private var customerstate = false
    private var workerstate =false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignuptypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // customer & worker 버튼 클릭 이벤트 toggle 처리

        binding.btnCustomer.setOnClickListener {
            if(customerstate){

                binding.btnCustomer.setImageResource(R.drawable.signup_customerbtn)
                customerstate = false

            }else{
                binding.btnCustomer.setImageResource(R.drawable.signup_customerbtn_on)
                customerstate = true

                // worker btn 비활성화
                workerstate = false
                binding.btnWorker.setImageResource(R.drawable.signup_workerbtn)
            }


            // customer OR worker 중 하나만 클릭해도, 다음버튼 활성화

            if(workerstate || customerstate){
                binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn_on)
                binding.btnNext.setTextColor(Color.parseColor("#FFFFFFFF"))
                binding.btnNext.isClickable = true
            }else{
                binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn)
                binding.btnNext.setTextColor(Color.parseColor("#D3D7DC"))
                binding.btnNext.isClickable = false
            }
        }

        binding.btnWorker.setOnClickListener {
            if(workerstate){

                binding.btnWorker.setImageResource(R.drawable.signup_workerbtn)
                workerstate = false

            }else{
                binding.btnWorker.setImageResource(R.drawable.signup_workerbtn)
                workerstate = true

                // customer btn 비활성화
                customerstate = false
                binding.btnCustomer.setImageResource(R.drawable.signup_customerbtn)
            }


            // customer OR worker 중 하나만 클릭해도, 다음버튼 활성화

            if(workerstate || customerstate){
                binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn_on)
                binding.btnNext.setTextColor(Color.parseColor("#FFFFFFFF"))
                binding.btnNext.isClickable = true
            }else{
                binding.btnNext.setBackgroundResource(R.drawable.shape_login_btn)
                binding.btnNext.setTextColor(Color.parseColor("#D3D7DC"))
                binding.btnNext.isClickable = false
            }
        }




        // 추천인 입력 edittext focus 이벤트 처리

        binding.etRecommend.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.setBackgroundResource(R.drawable.shape_login_et_focus)
            } else {
                view.setBackgroundResource(R.drawable.shape_login_et)
            }
        }


        // 다음버튼 클릭 이벤트 처리

        binding.btnNext.setOnClickListener {

            if(workerstate){
                SignupSingleton.signupPurpose = "4"
            }else{
                SignupSingleton.signupPurpose = "3"
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}