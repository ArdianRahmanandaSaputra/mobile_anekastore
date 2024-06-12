package com.example.anekastoremobile.data.remote.response

data class MakeOrderRequest(
    val deliveryoption: String,
    val service: String?,
    val courier: String?,
    val total: Int,
    val order: List<Int>
)