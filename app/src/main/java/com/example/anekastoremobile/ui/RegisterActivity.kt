package com.example.anekastoremobile.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anekastoremobile.data.remote.body.RegisterBody
import com.example.anekastoremobile.data.remote.response.RegisterResponse
import com.example.anekastoremobile.data.remote.retrofit.ApiConfig
import com.example.anekastoremobile.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            register()
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
        }
    }

    private fun register(){
        val email = binding.editTextText.text.toString()
        val password = binding.etPass.text.toString()
        val nama = binding.etNama.text.toString()

        if(email == "" || password == "" || nama == ""){
            Toast.makeText(this@RegisterActivity, "Isi Semua Form", Toast.LENGTH_SHORT).show()
            return
        }

       val body = RegisterBody(password,nama, email)
        val client = ApiConfig.getService().register(body)
        client.enqueue(object: Callback<RegisterResponse>{
            override fun onResponse(p0: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful){
                    val userPreferences = UserPreferences(this@RegisterActivity)
                    userPreferences.saveCredentials(email,password,response.body()?.accessToken!!)
                    startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
                }else{
                    Toast.makeText(this@RegisterActivity, "Gagal silahkan coba lagi", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<RegisterResponse>, p1: Throwable) {
                Toast.makeText(this@RegisterActivity, "Gagal terhubung ke Server.", Toast.LENGTH_SHORT).show()
            }

        })


    }
}