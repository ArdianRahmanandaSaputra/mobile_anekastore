package com.example.anekastoremobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class MakeOrderResponse(

    @SerializedName("snapToken")
    val snapToken: String? = null,
)
