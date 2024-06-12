package com.example.anekastoremobile.ui.homeui.edit_profile

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.anekastoremobile.R
import com.example.anekastoremobile.data.remote.response.City
import com.example.anekastoremobile.data.remote.response.CityResponse
import com.example.anekastoremobile.data.remote.response.MessageResponse
import com.example.anekastoremobile.data.remote.response.ProfileResponse
import com.example.anekastoremobile.data.remote.response.Province
import com.example.anekastoremobile.data.remote.response.ProvinceResponse
import com.example.anekastoremobile.data.remote.retrofit.ApiConfig
import com.example.anekastoremobile.databinding.ActivityEditProfileBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    private var data = ProfileResponse()
    private var dataGender: String? = null
    private var dataIdProvince: String? = null
    private var dataProvince: String? = null
    private var dataIdCity: String? = null
    private var dataCity: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Profil Saya"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val i = intent
        @Suppress("DEPRECATION")
        if (i != null) data = i.getParcelableExtra("edit_profile") ?: ProfileResponse()

        setupUI()
        binding.btnSave.setOnClickListener { save() }
    }

    private fun setupUI() {
        Glide.with(this)
            .load("https://storeaneka.my.id/uploads/user/${data.detail?.photo}")
            .error(R.drawable.baseline_person_24)
            .into(binding.photoProfile)
        binding.editName.setText(data.user?.name)
        binding.editEmail.setText(data.user?.email)
        binding.editNoHp.setText(data.detail?.phone)
        spinnerGender()
        getProvinces()
        binding.editPostalZip.setText(data.detail?.postalCode)
        binding.editDetailAddress.setText(data.detail?.detailAddress)

    }

    private fun spinnerGender() {
        val spinnerGender: Spinner = binding.spinnerGender

        val genderOptions = arrayOf("Pria", "Wanita")

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            genderOptions
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerGender.adapter = adapter

        val gender = data.detail?.gender
        val position = genderOptions.indexOfFirst { it.lowercase() == gender }
        if (position >= 0) {
            spinnerGender.setSelection(position)
        }

        spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedOption = parent.getItemAtPosition(position).toString()
                dataGender = selectedOption.lowercase(Locale.ROOT)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun getProvinces() {
        showLoading(true)
        val client = ApiConfig.getService(applicationContext).getProvince()
        client.enqueue(object : Callback<ProvinceResponse> {
            override fun onResponse(p0: Call<ProvinceResponse>, p1: Response<ProvinceResponse>) {
                showLoading(false)
                val responseBody = p1.body()
                if (p1.isSuccessful && responseBody != null) {
                    spinnerProvinces(responseBody.provinces)
                }
            }

            override fun onFailure(p0: Call<ProvinceResponse>, p1: Throwable) {
                showLoading(false)
                Log.e(TAG, p1.message.toString())
                Toast.makeText(
                    applicationContext,
                    "Gagal mengambil Data Provinsi.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun spinnerProvinces(listProvince: MutableList<Province>) {
        val spinnerProvince: Spinner = binding.spinnerProvince

        val provinceOptions = listProvince.map { it.province }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            provinceOptions
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerProvince.adapter = adapter

        val idProvince = data.detail?.province_code
        val position = listProvince.indexOfFirst { it.provinceId == idProvince }
        if (position >= 0) {
            spinnerProvince.setSelection(position)
        }

        spinnerProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedOption = parent.getItemAtPosition(position).toString()
                dataIdProvince = listProvince[position].provinceId
                dataProvince = selectedOption
                getCityByProvince(dataIdProvince?.toInt() ?: 0)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun getCityByProvince(provinceId: Int) {
        showLoading(true)
        val client = ApiConfig.getService(applicationContext).getCityByProvince(provinceId)
        client.enqueue(object : Callback<CityResponse> {
            override fun onResponse(p0: Call<CityResponse>, p1: Response<CityResponse>) {
                showLoading(false)
                val responseBody = p1.body()
                if (p1.isSuccessful && responseBody != null) {
                    spinnerCities(responseBody.cities)
                }
            }

            override fun onFailure(p0: Call<CityResponse>, p1: Throwable) {
                showLoading(false)
                Log.e(TAG, p1.message.toString())
                Toast.makeText(
                    applicationContext,
                    "Gagal mengambil Data Kota.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun spinnerCities(listCity: MutableList<City>) {
        val spinnerCity: Spinner = binding.spinnerCity

        val provinceOptions = listCity.map { it.cityName }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            provinceOptions
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerCity.adapter = adapter

        val idCity = data.detail?.cityCode
        val position = listCity.indexOfFirst { it.cityId == idCity }
        if (position >= 0) {
            spinnerCity.setSelection(position)
        }

        spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedOption = parent.getItemAtPosition(position).toString()
                dataIdCity = listCity[position].cityId
                dataCity = selectedOption

                binding.editPostalZip.setText(listCity[position].postalCode)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    private fun save() {
        val name = binding.editName.text.toString()
        val email = binding.editEmail.text.toString()
        val phone = binding.editNoHp.text.toString()
        val postalCode = binding.editPostalZip.text.toString()
        val detailAddress = binding.editDetailAddress.text.toString()
        val gender = dataGender
        val provinceCode = dataIdProvince
        val province = dataProvince
        val cityCode = dataIdCity
        val city = dataCity

        if (name.isNotEmpty() &&
            email.isNotEmpty() &&
            phone.isNotEmpty() &&
            postalCode.isNotEmpty() &&
            detailAddress.isNotEmpty() &&
            gender != null &&
            provinceCode != null &&
            province != null &&
            cityCode != null &&
            city != null
        ) {
            showLoading(true)
            val client = ApiConfig.getService(applicationContext).updateProfile(
                name,
                email,
                province,
                provinceCode,
                city,
                cityCode,
                phone,
                postalCode,
                detailAddress,
                gender
            )
            client.enqueue(object : Callback<MessageResponse> {
                override fun onResponse(p0: Call<MessageResponse>, p1: Response<MessageResponse>) {
                    showLoading(false)
                    val responseBody = p1.body()
                    if (p1.isSuccessful && responseBody != null) {
                        Toast.makeText(
                            this@EditProfileActivity,
                            responseBody.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        @Suppress("DEPRECATION")
                        onBackPressed()
                    }
                }

                override fun onFailure(p0: Call<MessageResponse>, p1: Throwable) {
                    showLoading(false)
                    Log.e(TAG, p1.message.toString())
                    Toast.makeText(
                        this@EditProfileActivity,
                        "Gagal terhubung ke Server, coba lagi!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        } else {
            Toast.makeText(
                this@EditProfileActivity,
                "Data tidak boleh ada yang kosong!",
                Toast.LENGTH_SHORT
            ).show()
        }
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
        private val TAG = EditProfileActivity::class.java.simpleName
    }
}