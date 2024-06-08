package com.example.anekastoremobile

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun formatToRupiah(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    format.minimumFractionDigits = 0
    format.maximumFractionDigits = 0
    return format.format(amount)
}

fun convertDateTime(input: String): String {
    // Format input string
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    // Parse the input date string
    val date = inputFormat.parse(input)

    // Output formats
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

    // Convert date to output formats
    val timeString = date?.let { timeFormat.format(it) }
    val dateString = date?.let { dateFormat.format(it) }

    // Combine time and date strings
    return "$timeString - $dateString"
}