@file:Suppress("DEPRECATED_ANNOTATION")

package com.example.anekastoremobile.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ProfileResponse(

    @SerializedName("user")
    val user: UserProfile? = null,

    @SerializedName("detail")
    val detail: DetailProfile? = null,
) : Parcelable

@Parcelize
data class UserProfile(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("email_verified_at")
    val emailVerifiedAt: @RawValue Any? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("update_at")
    val updateAt: String? = null,
) : Parcelable

@Parcelize
data class DetailProfile(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("user_id")
    val userId: String? = null,

    @SerializedName("photo")
    val photo: String? = null,

    @SerializedName("province")
    val province: String? = null,

    @SerializedName("province_code")
    val province_code: String? = null,

    @SerializedName("city")
    val city: String? = null,

    @SerializedName("city_code")
    val cityCode: String? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("postal_code")
    val postalCode: String? = null,

    @SerializedName("detail_address")
    val detailAddress: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("created_at")
    val created_date: String? = null,

    @SerializedName("updated_at")
    val updatedDate: String? = null
) : Parcelable