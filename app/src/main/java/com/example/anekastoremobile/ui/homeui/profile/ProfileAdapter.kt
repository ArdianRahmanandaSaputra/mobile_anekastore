package com.example.anekastoremobile.ui.homeui.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anekastoremobile.convertDateTime
import com.example.anekastoremobile.data.remote.response.Order
import com.example.anekastoremobile.databinding.ItemTransactionHistoryBinding
import com.example.anekastoremobile.formatToRupiah

class ProfileAdapter(val context: Context, private val dataList: List<Order>) :
    RecyclerView.Adapter<ProfileAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val _binding: ItemTransactionHistoryBinding) :
        RecyclerView.ViewHolder(_binding.root) {

        fun bind(data: Order) {
            _binding.tvDateBuy.text = convertDateTime(data.createdAt)
            _binding.tvStatus.text = data.statusPembayaran
            _binding.tvValueTotal.text = formatToRupiah(data.total.toDouble())

            if (data.orderDetails != null) {
                val orderDetailAdapter = OrderDetailAdapter(context, data.orderDetails)
                _binding.recyclerViewOrderDetails.adapter = orderDetailAdapter
                _binding.recyclerViewOrderDetails.layoutManager = LinearLayoutManager(context)
            }

            _binding.tvIdOrder.text = buildString {
                append("ID Order:\n")
                append(data.id)
            }
            _binding.copyClipboard.setOnClickListener {
                val clipboardManager =
                    context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText(
                    "aneka_store",
                    data.id
                )
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(context, "ID Order Disalin", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTransactionHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
        holder.itemView.setOnClickListener {

        }
    }
}