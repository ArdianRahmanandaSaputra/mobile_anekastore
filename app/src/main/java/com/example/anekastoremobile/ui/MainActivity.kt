package com.example.anekastoremobile.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anekastoremobile.data.remote.body.LoginBody
import com.example.anekastoremobile.data.remote.response.LoginResponse
import com.example.anekastoremobile.data.remote.retrofit.ApiConfig
import com.example.anekastoremobile.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userPreferences = UserPreferences(this@MainActivity)
        val token = userPreferences.getToken()
        if(token != null){
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
        }

        binding.button.setOnClickListener {
            login()
        }

    }

    private fun login(){
        val email =  binding.editTextText.text.toString()
        val pass = binding.editTextTextPassword.text.toString()

        if(email == "" || pass == ""){
            Toast.makeText(this@MainActivity, "Isi Semua Field", Toast.LENGTH_SHORT).show()
            return
        }

        val loginBody = LoginBody(pass, email)
        val client =  ApiConfig.getService().login(loginBody)
        client.enqueue(object: Callback<LoginResponse>{
            override fun onResponse(p0: Call<LoginResponse>, response: Response<LoginResponse>) {
              if(response.isSuccessful){
                  val userPreferences = UserPreferences(this@MainActivity)
                  userPreferences.saveCredentials(email,pass,response.body()?.accessToken!!)
                  startActivity(Intent(this@MainActivity, HomeActivity::class.java))
              }else{
                  Toast.makeText(this@MainActivity, "Email atau password salah", Toast.LENGTH_SHORT).show()
              }
            }

            override fun onFailure(p0: Call<LoginResponse>, p1: Throwable) {
                Log.e("MainActivityError", p1.message.toString())
            }

        })
    }
}