package com.example.anekastoremobile.ui.homeui.cart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anekastoremobile.data.remote.response.Cart
import com.example.anekastoremobile.data.remote.response.MessageResponse
import com.example.anekastoremobile.data.remote.retrofit.ApiConfig
import com.example.anekastoremobile.databinding.ItemCartBinding
import com.example.anekastoremobile.formatToRupiah
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartAdapter(
    private val context: Context,
    private val dataList: MutableList<Cart>,
    private val activity: CartActivity
) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val _binding: ItemCartBinding) :
        RecyclerView.ViewHolder(_binding.root) {

        fun bind(data: Cart) {
            Glide.with(context)
                .load("http://storeaneka.my.id/uploads/product/${data.item?.photo}")
                .into(_binding.ivCart)
            _binding.tvNameProduct.text = data.item?.name
            val price = data.item?.price?.toIntOrNull() ?: 0
            val amount = data.amount?.toIntOrNull() ?: 0
            _binding.tvPrice.text = formatToRupiah((price * amount).toDouble())
            _binding.tvQtyNumber.text = amount.toString()

            _binding.ivMinus.setOnClickListener {
                if (amount > 1) {
                    deleteItemCart(data.id ?: 0)
                } else {
                    Toast.makeText(context, "Minimal Pembelian 1", Toast.LENGTH_SHORT).show()
                }
            }
            _binding.ivPlus.setOnClickListener { addItemCart(data.id ?: 0) }
            _binding.ivDelete.setOnClickListener { deleteCart(data.id ?: 0) }
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

    private fun deleteItemCart(cartId: Int) {
        activity.showLoading(true)
        val client = ApiConfig.getService(context).deleteItemCart(cartId)
        client.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(p0: Call<MessageResponse>, p1: Response<MessageResponse>) {
                activity.showLoading(false)
                val responseBody = p1.body()
                if (p1.isSuccessful && responseBody != null) {
                    Toast.makeText(context, responseBody.message, Toast.LENGTH_SHORT).show()
                    activity.getData()
                } else {
                    Toast.makeText(context, responseBody?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<MessageResponse>, p1: Throwable) {
                activity.showLoading(false)
                Log.e("CartAdapter", p1.message.toString())
                Toast.makeText(context, "Gagal terhubung ke Server. Coba lagi!", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun addItemCart(cartId: Int) {
        activity.showLoading(true)
        val client = ApiConfig.getService(context).addItemCart(cartId)
        client.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(p0: Call<MessageResponse>, p1: Response<MessageResponse>) {
                activity.showLoading(false)
                val responseBody = p1.body()
                if (p1.isSuccessful && responseBody != null) {
                    Toast.makeText(context, responseBody.message, Toast.LENGTH_SHORT).show()
                    activity.getData()
                } else {
                    Toast.makeText(context, responseBody?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<MessageResponse>, p1: Throwable) {
                activity.showLoading(false)
                Log.e("CartAdapter", p1.message.toString())
                Toast.makeText(context, "Gagal terhubung ke Server. Coba lagi!", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun deleteCart(cartId: Int) {
        activity.showLoading(true)
        val client = ApiConfig.getService(context).deleteCart(cartId)
        client.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(p0: Call<MessageResponse>, p1: Response<MessageResponse>) {
                activity.showLoading(false)
                val responseBody = p1.body()
                if (p1.isSuccessful && responseBody != null) {
                    Toast.makeText(context, responseBody.message, Toast.LENGTH_SHORT).show()
                    activity.getData()
                } else {
                    Toast.makeText(context, responseBody?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<MessageResponse>, p1: Throwable) {
                activity.showLoading(false)
                Log.e("CartAdapter", p1.message.toString())
                Toast.makeText(context, "Gagal terhubung ke Server. Coba lagi!", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}