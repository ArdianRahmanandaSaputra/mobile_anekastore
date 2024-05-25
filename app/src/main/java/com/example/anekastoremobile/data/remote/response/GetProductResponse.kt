package com.example.anekastoremobile.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetProductResponse(

    @field:SerializedName("product")
    val product: MutableList<Product>? = null,
): Parcelable

@Parcelize
data class Product(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("categori_id")
    val categoryId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("photo")
    val photo: String? = null,

    @field:SerializedName("price")
    val price: String? = null,

    @field:SerializedName("weight")
    val weight: String? = null,

    @field:SerializedName("stock")
    val stock: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("category_name")
    val categoryName: String? = null,

    @field:SerializedName("terjual")
    val sold: Int? = null,

    @field:SerializedName("category")
    val category: Category? = null,
): Parcelable

@Parcelize
data class Category(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("photo")
    val photo: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,
): Parcelable