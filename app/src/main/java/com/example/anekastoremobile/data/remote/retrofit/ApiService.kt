package com.example.anekastoremobile.data.remote.retrofit

import com.example.anekastoremobile.data.remote.body.LoginBody
import com.example.anekastoremobile.data.remote.body.RegisterBody
import com.example.anekastoremobile.data.remote.response.GetCartResponse
import com.example.anekastoremobile.data.remote.response.GetProductResponse
import com.example.anekastoremobile.data.remote.response.LoginResponse
import com.example.anekastoremobile.data.remote.response.MessageResponse
import com.example.anekastoremobile.data.remote.response.ProductViewResponse
import com.example.anekastoremobile.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    fun login(@Body body: LoginBody): Call<LoginResponse>

    @POST("register")
    fun register(@Body body: RegisterBody): Call<RegisterResponse>

    @GET("getproduct")
    fun getProduct(): Call<GetProductResponse>

    @GET("product-view/{id}")
    fun productView(
        @Path("id") id: Int,
    ): Call<ProductViewResponse>

    @FormUrlEncoded
    @POST("add-cart")
    fun addCart(
        @Field("product_id") productId: Int,
        @Field("amount") amount: Int,
    ): Call<MessageResponse>

    @POST("get-cart")
    fun getCart(): Call<GetCartResponse>

}