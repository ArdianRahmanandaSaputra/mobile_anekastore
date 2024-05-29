package com.example.anekastoremobile

import java.text.NumberFormat
import java.util.Locale

fun formatToRupiah(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    format.minimumFractionDigits = 0
    format.maximumFractionDigits = 0
    return format.format(amount)
}