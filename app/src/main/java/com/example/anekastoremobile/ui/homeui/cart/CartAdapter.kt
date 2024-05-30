package com.example.anekastoremobile.ui.homeui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anekastoremobile.data.remote.response.Cart
import com.example.anekastoremobile.databinding.ItemCartBinding
import com.example.anekastoremobile.formatToRupiah

class CartAdapter(val context: Context, private val dataList: List<Cart>) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val _binding: ItemCartBinding) :
        RecyclerView.ViewHolder(_binding.root) {

        fun bind(data: Cart) {
            Glide.with(context)
                .load("http://storeaneka.my.id/uploads/product/${data.item?.photo}")
                .into(_binding.ivCart)
            _binding.tvNameProduct.text = data.item?.name
            _binding.tvPrice.text = formatToRupiah(data.item?.price?.toDouble() ?: 0.0)
            _binding.tvQtyNumber.text = data.amount
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartAdapter.MyViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}