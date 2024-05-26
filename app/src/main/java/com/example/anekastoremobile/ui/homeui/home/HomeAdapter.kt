package com.example.anekastoremobile.ui.homeui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anekastoremobile.data.remote.response.Product
import com.example.anekastoremobile.databinding.ItemProductBinding

class HomeAdapter(val context: Context, private val dataList: List<Product>) :
    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val _binding: ItemProductBinding) :
        RecyclerView.ViewHolder(_binding.root) {

        fun bind(data: Product) {
            Glide.with(context)
                .load("http://storeaneka.my.id/uploads/product/${data.photo}")
                .into(_binding.ivOffice)
            _binding.tvNameProduct.text = data.name
            _binding.tvPrice.text = buildString {
                append("Rp. ")
                append(data.price)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeAdapter.MyViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {
            val i = Intent(context, DetailProductActivity::class.java)
            i.putExtra("detail_product", data)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}