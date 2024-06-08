package com.example.anekastoremobile.ui.homeui.checkout

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anekastoremobile.data.remote.response.Cart
import com.example.anekastoremobile.data.remote.response.Product
import com.example.anekastoremobile.data.remote.response.ProductViewResponse
import com.example.anekastoremobile.data.remote.retrofit.ApiConfig
import com.example.anekastoremobile.databinding.ItemCheckoutBinding
import com.example.anekastoremobile.databinding.ItemProductBinding
import com.example.anekastoremobile.formatToRupiah
import com.example.anekastoremobile.ui.homeui.cart.CartActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutAdapter(
    val context: Context,
    private val dataList: List<Cart>,
    private val activity: CheckoutActivity
) :
    RecyclerView.Adapter<CheckoutAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val _binding: ItemCheckoutBinding) :
        RecyclerView.ViewHolder(_binding.root) {

        fun bind(data: Cart) {
            Glide.with(context)
                .load("http://storeaneka.my.id/uploads/product/${data.item?.photo}")
                .into(_binding.ivProduct)
            _binding.tvNameProduct.text = data.item?.name
            _binding.tvAmount.text = data.amount

            getDataDiscount(data.productId)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckoutAdapter.MyViewHolder {
        val binding =
            ItemCheckoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckoutAdapter.MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun getDataDiscount(productId: String?) {
        activity.showLoading(true)
        val client = ApiConfig.getService(activity).productView(productId?.toInt() ?: 0)
        client.enqueue(object : Callback<ProductViewResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                p0: Call<ProductViewResponse>,
                response: Response<ProductViewResponse>
            ) {
                activity.showLoading(false)
                val responseBody = response.body()?.product
                if (response.isSuccessful && responseBody != null) {
                   println(responseBody.discounts)
                }
            }

            override fun onFailure(p0: Call<ProductViewResponse>, p1: Throwable) {
                activity.showLoading(false)
                Log.e("CheckoutAdapter", p1.message.toString())
            }
        })
    }
}