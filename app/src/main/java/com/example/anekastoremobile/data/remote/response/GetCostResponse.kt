package com.example.anekastoremobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class GetCostResponse(
    @SerializedName("cost") val cost: MutableList<Cost>
)

data class Cost(
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("costs") val costs: MutableList<ShippingCost>
)

data class ShippingCost(
    @SerializedName("service") val service: String,
    @SerializedName("description") val description: String,
    @SerializedName("cost") val cost: MutableList<CostDetail>
)

data class CostDetail(
    @SerializedName("value") val value: Int,
    @SerializedName("etd") val etd: String,
    @SerializedName("note") val note: String
)