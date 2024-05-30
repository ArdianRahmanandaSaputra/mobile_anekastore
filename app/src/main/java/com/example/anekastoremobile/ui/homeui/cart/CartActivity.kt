package com.example.anekastoremobile.ui.homeui.cart

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

    private fun getData() {
        showLoading(true)
        val client = ApiConfig.getService(applicationContext).getCart()
        client.enqueue(object : Callback<GetCartResponse> {
            override fun onResponse(p0: Call<GetCartResponse>, p1: Response<GetCartResponse>) {
                showLoading(false)
                val responseBody = p1.body()
                if (p1.isSuccessful && responseBody != null) {
                    dataList = responseBody.cart ?: mutableListOf()
                    adapter = CartAdapter(applicationContext, dataList)
                    binding.rvCart.adapter = adapter
                }
            }

            override fun onFailure(p0: Call<GetCartResponse>, p1: Throwable) {
                showLoading(false)
                Log.e(TAG, p1.message.toString())
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