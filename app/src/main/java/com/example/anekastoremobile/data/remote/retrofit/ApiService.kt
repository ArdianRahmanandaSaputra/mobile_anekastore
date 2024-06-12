package com.example.anekastoremobile.data.remote.retrofit

import com.example.anekastoremobile.data.remote.body.LoginBody
import com.example.anekastoremobile.data.remote.body.RegisterBody
import com.example.anekastoremobile.data.remote.response.CityResponse
import com.example.anekastoremobile.data.remote.response.DetailProfile
import com.example.anekastoremobile.data.remote.response.GetCartResponse
import com.example.anekastoremobile.data.remote.response.GetCostResponse
import com.example.anekastoremobile.data.remote.response.GetProductResponse
import com.example.anekastoremobile.data.remote.response.LoginResponse
import com.example.anekastoremobile.data.remote.response.MakeOrderRequest
import com.example.anekastoremobile.data.remote.response.MakeOrderResponse
import com.example.anekastoremobile.data.remote.response.MessageResponse
import com.example.anekastoremobile.data.remote.response.ProductViewResponse
import com.example.anekastoremobile.data.remote.response.ProfileResponse
import com.example.anekastoremobile.data.remote.response.ProvinceResponse
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

    @POST("make-order")
    fun makeOrder(
        @Body request: MakeOrderRequest
    ): Call<MakeOrderResponse>

    @GET("get-province")
    fun getProvince(): Call<ProvinceResponse>

    @GET("get-city-by-province/{id}")
    fun getCityByProvince(
        @Path("id") idProvince: Int,
    ): Call<CityResponse>

    @FormUrlEncoded
    @POST("update-profile")
    fun updateProfile(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("province") province: String,
        @Field("province_code") province_code: String,
        @Field("city") city: String,
        @Field("city_code") cityCode: String,
        @Field("phone") phone: String,
        @Field("postal_code") postal_code: String,
        @Field("detail_address") detailAddress: String,
        @Field("gender") gender: String,
    ): Call<MessageResponse>
}