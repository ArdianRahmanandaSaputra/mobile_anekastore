package com.example.anekastoremobile.ui.homeui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.anekastoremobile.R
import com.example.anekastoremobile.data.remote.response.MessageResponse
import com.example.anekastoremobile.data.remote.response.Product
import com.example.anekastoremobile.data.remote.response.ProductViewResponse
import com.example.anekastoremobile.data.remote.retrofit.ApiConfig
import com.example.anekastoremobile.databinding.ActivityDetailProductBinding
import com.example.anekastoremobile.formatToRupiah
import com.example.anekastoremobile.ui.homeui.cart.CartActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            actionBar.setDisplayShowCustomEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val i = intent
        @Suppress("DEPRECATION")
        if (i != null) product = i.getParcelableExtra("detail_product") ?: Product()

        getData()
    }

    private fun getData() {
        showLoading(true)
        val client = ApiConfig.getService(applicationContext).productView(product.id ?: 0)
        client.enqueue(object : Callback<ProductViewResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                p0: Call<ProductViewResponse>,
                response: Response<ProductViewResponse>
            ) {
                showLoading(false)
                val responseBody = response.body()?.product
                if (response.isSuccessful && responseBody != null) {
                    Glide.with(applicationContext)
                        .load("http://storeaneka.my.id/uploads/product/${responseBody.photo}")
                        .into(binding.ivProduct)
                    binding.tvPrice.text = formatToRupiah(responseBody.price?.toDouble() ?: 0.0)
                    binding.tvNameProduct.text = responseBody.name
                    binding.valueNoRegister.text = responseBody.id.toString()
                    binding.valueCategory.text = responseBody.categoryName
                    if ((responseBody.discounts?.size ?: 0) > 0) {
                        val descriptions = StringBuilder()
                        for (i in 0 until (responseBody.discounts?.size ?: 0)) {
                            descriptions.append(responseBody.discounts?.get(i)?.description ?: "")
                            if (i != (responseBody.discounts?.size ?: 0) - 1) {
                                descriptions.append("\n")
                            }
                        }
                        binding.valuePacket.text = descriptions.toString()
                        binding.btnAddtoCart.setOnClickListener { addCart(responseBody.id ?: 0) }
                    } else {
                        binding.valuePacket.text = "-"
                    }
                    binding.valueDesc.text = responseBody.description
                }
            }

            override fun onFailure(p0: Call<ProductViewResponse>, p1: Throwable) {
                showLoading(false)
                Log.e("DetailProductActivity", p1.message.toString())
            }
        })
    }

    private fun addCart(productId: Int) {
        showLoading(true)
        val client = ApiConfig.getService(applicationContext).addCart(productId, 1)
        client.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(
                p0: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                showLoading(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Toast.makeText(applicationContext, responseBody.message, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, responseBody?.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(p0: Call<MessageResponse>, p1: Throwable) {
                showLoading(false)
                Log.e("DetailProductActivity", p1.message.toString())
                Toast.makeText(
                    applicationContext,
                    "Gagal terhubung ke Server. Coba lagi!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_product, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        } else if (item.itemId == R.id.menu_cart) {
            val i = Intent(this, CartActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(i)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}