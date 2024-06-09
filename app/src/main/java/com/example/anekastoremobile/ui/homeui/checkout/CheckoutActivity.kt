package com.example.anekastoremobile.ui.homeui.checkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anekastoremobile.data.remote.response.Cart
import com.example.anekastoremobile.data.remote.response.GetCostResponse
import com.example.anekastoremobile.data.remote.response.MakeOrderRequest
import com.example.anekastoremobile.data.remote.response.MakeOrderResponse
import com.example.anekastoremobile.data.remote.response.ProfileResponse
import com.example.anekastoremobile.data.remote.response.ShippingCost
import com.example.anekastoremobile.data.remote.retrofit.ApiConfig
import com.example.anekastoremobile.databinding.ActivityCheckoutBinding
import com.example.anekastoremobile.formatToRupiah
import com.example.anekastoremobile.ui.WebViewActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding

    private lateinit var dataCheckout: MutableList<Cart>
    private lateinit var adapter: CheckoutAdapter
    private var dest: String? = "290"

    private var reqDeliveryOption: String? = null
    private var reqService: String? = null
    private var reqCourier: String? = null
    private var orderList: List<Int> = mutableListOf()

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

        totalAmount = 0
        totalWeight = 0
        totalPrice = 0
        val i = intent
        @Suppress("DEPRECATION")
        if (i != null) dataCheckout = i.getParcelableArrayListExtra("checkout") ?: ArrayList()

        getDataAddress()
        binding.rvCheckout.layoutManager = LinearLayoutManager(this)
        binding.rvCheckout.setHasFixedSize(true)
        adapter = CheckoutAdapter(this, dataCheckout, this@CheckoutActivity)
        binding.rvCheckout.adapter = adapter
        orderList = dataCheckout.map { it.id ?: 0 }
        println(orderList)
        spinnerDelivery()
    }

    fun syncData() {
        binding.qtyTotal.text = totalAmount.toString()
        binding.totalPrice.text = formatToRupiah(totalPrice.toDouble())
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
                    dest = responseBody.detail?.cityCode
                }
            }

            override fun onFailure(p0: Call<ProfileResponse>, p1: Throwable) {
                showLoading(false)
                Log.e(TAG, p1.message.toString())
                Toast.makeText(
                    this@CheckoutActivity,
                    "Gagal mengambil Data Alamat Pengiriman, coba lagi!",
                    Toast.LENGTH_SHORT
                ).show()
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

    private fun spinnerDelivery() {
        val spinnerDeliveryOption: Spinner = binding.spinnerDeliveryOption

        val deliveryOptions = arrayOf("Pilih Pengiriman", "Pengiriman Toko", "Pengiriman Kurir")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            deliveryOptions
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerDeliveryOption.adapter = adapter

        spinnerDeliveryOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedOption = parent.getItemAtPosition(position).toString()
                handleDeliveryOption(selectedOption)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun handleDeliveryOption(option: String) {
        when (option) {
            "Pengiriman Toko" -> {
                Toast.makeText(this, "Anda memilih Pengiriman Toko", Toast.LENGTH_SHORT).show()
                binding.layoutCourier.visibility = View.GONE
                binding.layoutService.visibility = View.GONE
                reqDeliveryOption = "toko"
                binding.btnCreateOrder.setOnClickListener {
                    makeOrder(totalPrice)
                }
            }

            "Pengiriman Kurir" -> {
                Toast.makeText(this, "Anda memilih Pengiriman Kurir", Toast.LENGTH_SHORT).show()
                binding.layoutCourier.visibility = View.VISIBLE
                binding.layoutService.visibility = View.VISIBLE
                reqDeliveryOption = "kurir"
                spinnerCourier()
                val dataServiceTemp = arrayOf("Pilih Service")
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    dataServiceTemp
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerServiceOption.adapter = adapter
            }
        }
    }

    private fun spinnerCourier() {
        val spinnerDeliveryOption: Spinner = binding.spinnerCourierOption

        val deliveryOptions = arrayOf("Pilih Kurir", "JNE", "TIKI", "POS")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            deliveryOptions
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerDeliveryOption.adapter = adapter

        spinnerDeliveryOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedOption = parent.getItemAtPosition(position).toString()
                handleCourierOption(selectedOption)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun handleCourierOption(option: String) {
        when (option) {
            "JNE" -> {
                Toast.makeText(this, "Anda memilih Kurir $option", Toast.LENGTH_SHORT).show()
                getCost(option.lowercase())
            }

            "TIKI" -> {
                Toast.makeText(this, "Anda memilih Kurir $option", Toast.LENGTH_SHORT).show()
                getCost(option.lowercase())
            }

            "POS" -> {
                Toast.makeText(this, "Anda memilih Kurir $option", Toast.LENGTH_SHORT).show()
                getCost(option.lowercase())
            }
        }
    }

    private fun getCost(courier: String) {
        reqCourier = courier
        showLoading(true)
        val client = ApiConfig.getService(applicationContext)
            .getCost(courier, dest.toString(), totalWeight.toString())
        client.enqueue(object : Callback<GetCostResponse> {
            override fun onResponse(p0: Call<GetCostResponse>, p1: Response<GetCostResponse>) {
                showLoading(false)
                val responseBody = p1.body()
                if (p1.isSuccessful && responseBody != null) {
                    spinnerService(responseBody.cost[0].costs)
                }
            }

            override fun onFailure(p0: Call<GetCostResponse>, p1: Throwable) {
                showLoading(false)
                Log.e(TAG, p1.message.toString())
                Toast.makeText(
                    this@CheckoutActivity,
                    "Gagal terhubung ke Server, coba lagi!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun spinnerService(dataList: MutableList<ShippingCost>) {
        val spinnerDeliveryOption: Spinner = binding.spinnerServiceOption

        val dataService = mutableListOf("Pilih Service")
        dataService.addAll(dataList.map { "${it.service} | ${it.cost[0].etd} Hari | ${it.cost[0].value}" })

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            dataService
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerDeliveryOption.adapter = adapter

        spinnerDeliveryOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedOption = parent.getItemAtPosition(position).toString()
                if (position != 0) {
                    val selected = dataList[position - 1].cost[0].value
                    handleServiceOption(selectedOption, selected)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun handleServiceOption(option: String, costValue: Int) {
        if (option != "Pilih Service") {
            val total = totalPrice + costValue
            binding.totalPrice.text = formatToRupiah(total.toDouble())
            Toast.makeText(this, "Anda memilih Service $option", Toast.LENGTH_SHORT).show()
            reqService = option
            binding.btnCreateOrder.setOnClickListener {
                makeOrder(total)
            }
        }
    }

    private fun makeOrder(fixTotal: Int) {
        println(reqDeliveryOption)
        println(reqService)
        println(reqCourier)
        println(fixTotal)
        println(orderList)
        val makeOrderRequest = MakeOrderRequest(
            user_id = 2,
            deliveryoption = reqDeliveryOption.toString(),
            service = reqService.toString(),
            courier = reqCourier.toString(),
            total = fixTotal,
            order = orderList
        )
        showLoading(true)
        val client =
            ApiConfig.getService(applicationContext).makeOrder(makeOrderRequest)
        client.enqueue(object : Callback<MakeOrderResponse> {
            override fun onResponse(p0: Call<MakeOrderResponse>, p1: Response<MakeOrderResponse>) {
                showLoading(false)
                val responseBody = p1.body()
                if (p1.isSuccessful && responseBody != null) {
                    println(responseBody.snapToken)
                    val i = Intent(this@CheckoutActivity, WebViewActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    i.putExtra("url", responseBody.snapToken)
                    startActivity(i)
                    finish()
                }
            }

            override fun onFailure(p0: Call<MakeOrderResponse>, p1: Throwable) {
                showLoading(false)
                Log.e(TAG, p1.message.toString())
                Toast.makeText(
                    this@CheckoutActivity,
                    "Gagal terhubung ke Server, coba lagi!",
                    Toast.LENGTH_SHORT
                ).show()
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
        private val TAG = CheckoutActivity::class.java.simpleName
        var totalAmount = 0
        var totalWeight = 0
        var totalPrice = 0
    }
}