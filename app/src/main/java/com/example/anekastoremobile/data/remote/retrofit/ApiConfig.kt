package com.example.anekastoremobile.data.remote.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getService(): ApiService{
       val loggingInterceptor = HttpLoggingInterceptor()
           .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retofit = Retrofit.Builder()
            .baseUrl("http://storeaneka.my.id/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retofit.create(ApiService::class.java)
    }
}