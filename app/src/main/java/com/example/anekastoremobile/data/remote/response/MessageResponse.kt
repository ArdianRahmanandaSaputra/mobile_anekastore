package com.example.anekastoremobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class MessageResponse(

    @field:SerializedName("message")
    val message: String? = null,
)
