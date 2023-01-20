package com.week2.easytask.login.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.week2.easytask.CustomToast.showCustomToast
import com.week2.easytask.R
import com.week2.easytask.Retrofit
import com.week2.easytask.databinding.ActivityFindpwsuccessBinding
import com.week2.easytask.login.model.SendEmailData
import com.week2.easytask.login.network.SendEmailAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindpwSuccessActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFindpwsuccessBinding
    private var sendEmailRetro = Retrofit.getInstance().create(SendEmailAPI::class.java)
    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindpwsuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // FindpwActivity 의 intent 에서 email 값 받아오기

        if(intent.hasExtra("email")){
            email = intent.getStringExtra("email").toString()
        }
        

        // 뒤로가기 버튼 클릭 이벤트 처리  

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, FindpwActivity::class.java)
            startActivity(intent)
        }

        // 로그인 버튼 클릭 이벤트 처리

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // 이메일 재전송 버튼 클릭 이벤트 처리
        // -> 성공시 ToastMessage 띄우기

        binding.btnAgainemail.setOnClickListener {
            val datas = SendEmailData(email)
            sendEmailRetro.sendeamil(datas)
                .enqueue(object : Callback<SendEmailData>{
                    override fun onResponse(
                        call: Call<SendEmailData>,
                        response: Response<SendEmailData>
                    ) {
                        if(response.code() == 200){
                            // 재전송 성공시 Custom TOast message
                            Toast(this@FindpwSuccessActivity).showCustomToast ("이메일을 다시 발송했어요.", this@FindpwSuccessActivity)
                        }else{

                        }
                    }

                    override fun onFailure(call: Call<SendEmailData>, t: Throwable) {}
                })
        }
    }
}