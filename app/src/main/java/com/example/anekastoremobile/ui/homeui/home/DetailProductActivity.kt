package com.example.anekastoremobile.ui.homeui.home

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.anekastoremobile.data.remote.response.Product
import com.example.anekastoremobile.databinding.ActivityDetailProductBinding

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

        println(product)
        Glide.with(this)
            .load("http://storeaneka.my.id/uploads/product/${product.photo}")
            .into(binding.ivProduct)
        binding.tvPrice.text = product.price
        binding.tvNameProduct.text = product.name
    }
}