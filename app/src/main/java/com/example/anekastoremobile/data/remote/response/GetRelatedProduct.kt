package com.example.anekastoremobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetRelatedProduct(

    @SerializedName("relatedProduct")
    val relatedProduct: List<Product> = mutableListOf(),
)
