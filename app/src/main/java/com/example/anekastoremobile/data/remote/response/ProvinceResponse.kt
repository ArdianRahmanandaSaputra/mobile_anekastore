package com.example.anekastoremobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProvinceResponse(

    @SerializedName("provinces")
    val provinces: MutableList<Province> = mutableListOf(),
)

data class Province(

    @SerializedName("province_id")
    val provinceId: String? = null,

    @SerializedName("province")
    val province: String? = null,
)
