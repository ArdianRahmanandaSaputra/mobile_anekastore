package com.example.anekastoremobile.data.remote.retrofit

import com.example.anekastoremobile.data.remote.body.LoginBody
import com.example.anekastoremobile.data.remote.body.RegisterBody
import com.example.anekastoremobile.data.remote.response.GetCartResponse
import com.example.anekastoremobile.data.remote.response.GetCostResponse
import com.example.anekastoremobile.data.remote.response.GetProductResponse
import com.example.anekastoremobile.data.remote.response.LoginResponse
import com.example.anekastoremobile.data.remote.response.MakeOrderResponse
import com.example.anekastoremobile.data.remote.response.MessageResponse
import com.example.anekastoremobile.data.remote.response.ProductViewResponse
import com.example.anekastoremobile.data.remote.response.ProfileResponse
import com.example.anekastoremobile.data.remote.response.RegisterResponse
import com.example.anekastoremobile.data.remote.response.TransactionHistory
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

    @FormUrlEncoded
    @POST("delete-item-cart")
    fun deleteItemCart(
        @Field("cart_id") cartId: Int,
    ): Call<MessageResponse>

    @FormUrlEncoded
    @POST("add-item-cart")
    fun addItemCart(
        @Field("cart_id") cartId: Int,
    ): Call<MessageResponse>

    @FormUrlEncoded
    @POST("delete-cart")
    fun deleteCart(
        @Field("cart_id") cartId: Int,
    ): Call<MessageResponse>

    @GET("profile")
    fun profile(): Call<ProfileResponse>

    @GET("orders-by-customers")
    fun transactionHistory(): Call<TransactionHistory>

    @FormUrlEncoded
    @POST("get-cost")
    fun getCost(
        @Field("courier") courier: String,
        @Field("dest") dest: String,
        @Field("weight") weight: String,
    ): Call<GetCostResponse>

    @FormUrlEncoded
    @POST("make-order")
    fun makeOrder(
        @Field("user_id") user_id: Int,
        @Field("deliveryoption") deliveryOption: String,
        @Field("service") service: String?,
        @Field("courier") courier: String?,
        @Field("total") total: Int,
        @Field("order") order: List<Int>
    ): Call<MakeOrderResponse>
}