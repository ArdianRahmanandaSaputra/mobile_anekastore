package com.example.anekastoremobile.data.remote.retrofit

import com.example.anekastoremobile.data.remote.body.LoginBody
import com.example.anekastoremobile.data.remote.body.RegisterBody
import com.example.anekastoremobile.data.remote.response.GetProductResponse
import com.example.anekastoremobile.data.remote.response.LoginResponse
import com.example.anekastoremobile.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login(@Body body: LoginBody): Call<LoginResponse>

    @POST("register")
    fun register(@Body body: RegisterBody): Call<RegisterResponse>

    @GET("getproduct")
    fun getProduct(): Call<GetProductResponse>
}