package com.example.anekastoremobile.ui.homeui.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anekastoremobile.R
import com.example.anekastoremobile.data.remote.response.OrderDetail
import com.example.anekastoremobile.formatToRupiah

class OrderDetailAdapter(
    private val context: Context,
    private val orderDetails: List<OrderDetail>
) : RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {

    class OrderDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(orderDetail: OrderDetail, context: Context) {
            val ivProduct = itemView.findViewById<ImageView>(R.id.iv_product)
            val tvNameProduct = itemView.findViewById<TextView>(R.id.tv_name_product)
            val tvAmount = itemView.findViewById<TextView>(R.id.tv_amount)
            val tvPrice = itemView.findViewById<TextView>(R.id.tv_price)
            val tvSubTotal = itemView.findViewById<TextView>(R.id.tv_sub_total)

            Glide.with(context)
                .load("http://storeaneka.my.id/uploads/product/${orderDetail.productDetail.photo}")
                .into(ivProduct)
            tvNameProduct.text = orderDetail.productDetail.name
            tvAmount.text = orderDetail.amount
            tvPrice.text = orderDetail.productPrice
            tvSubTotal.text = formatToRupiah(orderDetail.subtotal.toDouble())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_order_detail, parent, false)
        return OrderDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.bind(orderDetails[position], context)
    }

    override fun getItemCount(): Int {
        return orderDetails.size
    }
}