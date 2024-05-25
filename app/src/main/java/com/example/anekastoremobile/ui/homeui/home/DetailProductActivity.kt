package com.example.anekastoremobile.ui.homeui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.anekastoremobile.R
import com.example.anekastoremobile.data.remote.response.Product
import com.example.anekastoremobile.databinding.ActivityDetailProductBinding

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        supportActionBar?.hide()

        val i = intent
        @Suppress("DEPRECATION")
        if (i != null) product = i.getParcelableExtra("detail_product") ?: Product()

        println(product)
        Glide.with(this)
            .load("http://storeaneka.my.id/uploads/product/${product.photo}")
            .into(binding.ivProduct)
        binding.tvPrice.text = product.price
    }
}