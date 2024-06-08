package com.example.anekastoremobile.ui.homeui.checkout

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anekastoremobile.data.remote.response.Cart
import com.example.anekastoremobile.data.remote.response.Discount
import com.example.anekastoremobile.data.remote.response.ProductViewResponse
import com.example.anekastoremobile.data.remote.retrofit.ApiConfig
import com.example.anekastoremobile.databinding.ItemCheckoutBinding
import com.example.anekastoremobile.formatToRupiah
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CheckoutAdapter(
    val context: Context,
    private val dataList: List<Cart>,
    private val activity: CheckoutActivity
) :
    RecyclerView.Adapter<CheckoutAdapter.MyViewHolder>() {

    companion object {
        private var TAG = CheckoutAdapter::class.java.simpleName
    }

    private var discount: MutableList<Discount>? = mutableListOf()

    inner class MyViewHolder(private val _binding: ItemCheckoutBinding) :
        RecyclerView.ViewHolder(_binding.root) {

        fun bind(data: Cart) {
            Glide.with(context)
                .load("http://storeaneka.my.id/uploads/product/${data.item?.photo}")
                .into(_binding.ivProduct)
            _binding.tvNameProduct.text = data.item?.name
            _binding.tvAmount.text = data.amount

            loadDiscountAndCalculateTotal(data)
        }

        private fun loadDiscountAndCalculateTotal(data: Cart) {
            // Menggunakan coroutine untuk memuat data discount
            activity.lifecycleScope.launch {
                val discount = withContext(Dispatchers.IO) {
                    getDataDiscount(data.productId)
                }

                val amount = data.amount?.toInt() ?: 0
                val price = data.product?.price?.toInt() ?: 0
                val weight = data.product?.weight?.toInt() ?: 0
                var applicableDiscount = 0

                var tempTotal = 0
                var weightTotal = 0

                for (item in discount ?: mutableListOf()) {
                    val constraint = item.constraint?.toInt() ?: 0
                    val discountValue = item.discounts?.toInt() ?: 0

                    if (amount >= constraint) {
                        applicableDiscount = discountValue
                    }
                }

                tempTotal += (price - applicableDiscount) * amount
                weightTotal += amount * weight

                _binding.tvPrice.text = formatToRupiah(tempTotal.toDouble())
                CheckoutActivity.totalAmount += amount
                CheckoutActivity.totalWeight += weightTotal
                CheckoutActivity.totalPrice += tempTotal

                activity.syncData()
            }
        }

        private suspend fun getDataDiscount(productId: String?): List<Discount>? {
            activity.showLoading(true)
            return suspendCoroutine { continuation ->
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
                            discount = responseBody.discounts
                            continuation.resume(responseBody.discounts)
                        } else {
                            continuation.resume(emptyList())
                        }
                    }

                    override fun onFailure(p0: Call<ProductViewResponse>, p1: Throwable) {
                        activity.showLoading(false)
                        Log.e(TAG, p1.message.toString())
                        continuation.resume(emptyList())
                    }
                })
            }
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
}