package com.example.anekastoremobile.ui.homeui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.anekastoremobile.R
import com.example.anekastoremobile.data.remote.response.Product
import com.example.anekastoremobile.data.remote.response.ProductViewResponse
import com.example.anekastoremobile.data.remote.retrofit.ApiConfig
import com.example.anekastoremobile.databinding.ActivityDetailProductBinding
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
        val client = ApiConfig.getService().productView(product.id ?: 0)
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
                    binding.tvPrice.text = buildString {
                        append("Rp. ")
                        append(responseBody.price)
                    }
                    binding.tvNameProduct.text = responseBody.name
                    binding.valueNoRegister.text = responseBody.name
                    binding.valueBrand.text = responseBody.categoryName
                    binding.valueExpired.text = responseBody.name
                    binding.valueDesc.text = responseBody.description
                }
            }

            override fun onFailure(p0: Call<ProductViewResponse>, p1: Throwable) {
                showLoading(false)
                Log.e("DetailProductActivity", p1.message.toString())
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
            println("check")
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}