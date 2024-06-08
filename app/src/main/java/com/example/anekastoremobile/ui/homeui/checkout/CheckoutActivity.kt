package com.example.anekastoremobile.ui.homeui.checkout

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.example.anekastoremobile.data.remote.response.Cart
import com.example.anekastoremobile.data.remote.response.ProductViewResponse
import com.example.anekastoremobile.data.remote.response.ProfileResponse
import com.example.anekastoremobile.data.remote.retrofit.ApiConfig
import com.example.anekastoremobile.databinding.ActivityCheckoutBinding
import com.example.anekastoremobile.formatToRupiah
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding

    private lateinit var dataCheckout: ArrayList<Cart>
    private lateinit var adapter: CheckoutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
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
        if (i != null) dataCheckout = i.getParcelableArrayListExtra("checkout") ?: ArrayList()

        getDataAddress()
        binding.rvCheckout.layoutManager = LinearLayoutManager(this)
        binding.rvCheckout.setHasFixedSize(true)
        adapter = CheckoutAdapter(this, dataCheckout)
        binding.rvCheckout.adapter = adapter
    }

    private fun getDataAddress() {
        showLoading(true)
        val client = ApiConfig.getService(this).profile()
        client.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(p0: Call<ProfileResponse>, p1: Response<ProfileResponse>) {
                showLoading(false)
                val responseBody = p1.body()
                if (p1.isSuccessful && responseBody != null) {
                    viewUIAddress(responseBody)
                }
            }

            override fun onFailure(p0: Call<ProfileResponse>, p1: Throwable) {
                showLoading(false)
                Log.e("CheckoutActivity", p1.message.toString())
            }
        })
    }

    private fun viewUIAddress(data: ProfileResponse) {
        binding.nameUser.text = data.user?.name
        binding.phone.text = data.detail?.phone
        binding.address.text =
            buildString {
                append(data.detail?.detailAddress)
                append(", ")
                append(data.detail?.city)
                append(", ")
                append(data.detail?.province)
                append(" ")
                append(data.detail?.postalCode)
            }
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
}