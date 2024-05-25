package com.example.anekastoremobile.ui.homeui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.GetChars
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.anekastoremobile.data.remote.response.GetProductResponse
import com.example.anekastoremobile.data.remote.response.LoginResponse
import com.example.anekastoremobile.data.remote.response.Product
import com.example.anekastoremobile.data.remote.retrofit.ApiConfig
import com.example.anekastoremobile.databinding.FragmentHomeBinding
import com.example.anekastoremobile.ui.HomeActivity
import com.example.anekastoremobile.ui.UserPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: HomeAdapter
    private var dataList = mutableListOf<Product>()
    private var filteredList: MutableList<Product> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.rvProduct?.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = HomeAdapter(requireContext(), filteredList)
        _binding?.rvProduct?.adapter = adapter

        getData()
        searchData()
    }

    private fun getData() {
        val client = ApiConfig.getService().getProduct()
        client.enqueue(object : Callback<GetProductResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                p0: Call<GetProductResponse>,
                response: Response<GetProductResponse>
            ) {
                if (response.isSuccessful) {
                    dataList = response.body()?.product ?: mutableListOf()
                    filteredList.clear()
                    filteredList.addAll(dataList)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(p0: Call<GetProductResponse>, p1: Throwable) {
                Log.e("HomeFragment", p1.message.toString())
            }
        })
    }

    private fun searchData() {
        _binding?.etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filterData(s.toString())
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterData(query: String) {
        val filtered = dataList.filter {
            it.name?.contains(query, ignoreCase = true) == true || it.price?.contains(
                query,
                ignoreCase = true
            ) == true
        }
        filteredList.clear()
        filteredList.addAll(filtered)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}