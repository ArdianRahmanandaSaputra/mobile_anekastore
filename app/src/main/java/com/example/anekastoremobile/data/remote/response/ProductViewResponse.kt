package com.example.anekastoremobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProductViewResponse(

    @field:SerializedName("product")
    val product: Product? = null,
)
