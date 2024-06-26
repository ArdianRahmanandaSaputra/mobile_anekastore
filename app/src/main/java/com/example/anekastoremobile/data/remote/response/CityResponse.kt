package com.example.anekastoremobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class CityResponse(

    @SerializedName("cities")
    val cities: MutableList<City> = mutableListOf(),
)

data class City(

    @SerializedName("city_id")
    val cityId: String? = null,

    @SerializedName("province_id")
    val provinceId: String? = null,

    @SerializedName("province")
    val province: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("city_name")
    val cityName: String? = null,

    @SerializedName("postal_code")
    val postalCode: String? = null,
)
