package com.example.anekastoremobile.data.remote.response

import com.google.gson.annotations.SerializedName

data class TransactionHistory(
    @SerializedName("order") val order: MutableList<Order>,
)

data class Order(
    @SerializedName("id") val id: String,
    @SerializedName("user_id") val userId: String,
    @SerializedName("status") val status: String,
    @SerializedName("total") val total: String,
    @SerializedName("status_pembayaran") val statusPembayaran: String,
    @SerializedName("snap_token") val snapToken: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("detail") val detail: List<OrderDetail>,
    @SerializedName("price") val price: String,
    @SerializedName("orderdetails") val orderDetails: List<OrderDetail>? = null,
    @SerializedName("shipment") val shipment: Shipment
)

data class OrderDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("order_id") val orderId: String,
    @SerializedName("product_id") val productId: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("subtotal") val subtotal: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("product_price") val productPrice: String,
    @SerializedName("product_detail") val productDetail: ProductDetail,
    @SerializedName("product") val product: Product
)

data class ProductDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("categori_id") val categoryId: String,
    @SerializedName("name") val name: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("price") val price: String,
    @SerializedName("weight") val weight: String,
    @SerializedName("stock") val stock: String,
    @SerializedName("description") val description: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)

data class Shipment(
    @SerializedName("id") val id: Int,
    @SerializedName("order_id") val orderId: String,
    @SerializedName("price") val price: String,
    @SerializedName("courier") val courier: String,
    @SerializedName("estimate") val estimate: String,
    @SerializedName("service") val service: String,
    @SerializedName("resi") val resi: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)
