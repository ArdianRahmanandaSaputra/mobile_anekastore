package com.example.anekastoremobile.ui.homeui.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anekastoremobile.data.remote.response.Cart
import com.example.anekastoremobile.data.remote.response.GetCartResponse
import com.example.anekastoremobile.data.remote.retrofit.ApiConfig
import com.example.anekastoremobile.databinding.ActivityCartBinding
import com.example.anekastoremobile.formatToRupiah
import com.example.anekastoremobile.ui.homeui.checkout.CheckoutActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    private lateinit var adapter: CartAdapter
    private var dataList = mutableListOf<Cart>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            actionBar.setDisplayShowCustomEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        binding.rvCart.layoutManager = LinearLayoutManager(this)
        binding.rvCart.setHasFixedSize(true)
        getData()
    }

    fun getData() {
        showLoading(true)
        val client = ApiConfig.getService(applicationContext).getCart()
        client.enqueue(object : Callback<GetCartResponse> {
            override fun onResponse(p0: Call<GetCartResponse>, p1: Response<GetCartResponse>) {
                showLoading(false)
                val responseBody = p1.body()
                if (p1.isSuccessful && responseBody != null) {
                    dataList = responseBody.cart ?: mutableListOf()
                    adapter = CartAdapter(applicationContext, dataList, this@CartActivity)
                    binding.rvCart.adapter = adapter

                    var amount = 0
                    var totalPrice = 0
                    if (responseBody.cart != null) {
                        for (i in 0 until responseBody.cart.size) {
                            val itemAmount = responseBody.cart[i].amount?.toInt() ?: 0
                            amount += itemAmount

                            val itemTotal =
                                responseBody.cart[i].amount?.toInt()?.let { amountItem ->
                                    responseBody.cart[i].item?.price?.toInt()?.let { price ->
                                        amountItem * price
                                    }
                                } ?: 0
                            totalPrice += itemTotal
                        }
                    }
                    binding.qtyTotal.text = amount.toString()
                    binding.totalPrice.text = formatToRupiah(totalPrice.toDouble())

                    binding.btnCheckout.setOnClickListener {
                        val i = Intent(this@CartActivity, CheckoutActivity::class.java)
                        i.putParcelableArrayListExtra(
                            "checkout",
                            ArrayList(responseBody.cart ?: ArrayList())
                        )
                        startActivity(i)
                    }
                }
            }

            override fun onFailure(p0: Call<GetCartResponse>, p1: Throwable) {
                showLoading(false)
                Log.e(TAG, p1.message.toString())
            }
        })
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = CartActivity::class.java.simpleName
    }
}