package com.example.anekastoremobile.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetCartResponse(

    @field:SerializedName("cart")
    val cart: MutableList<Cart>? = null,
) : Parcelable

@Parcelize
data class Cart(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("user_id")
    val userId: String? = null,

    @SerializedName("product_id")
    val productId: String? = null,

    @SerializedName("amount")
    val amount: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("item")
    val item: Item? = null,

    @SerializedName("product")
    val product: Product? = null,
) : Parcelable

@Parcelize
data class Item(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("categori_id")
    val categoryId: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("photo")
    val photo: String? = null,

    @SerializedName("price")
    val price: String? = null,

    @SerializedName("weight")
    val weight: String? = null,

    @SerializedName("stock")
    val stock: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,
) : Parcelable