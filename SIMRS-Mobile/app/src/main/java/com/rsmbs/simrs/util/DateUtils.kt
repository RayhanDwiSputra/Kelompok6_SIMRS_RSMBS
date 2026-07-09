package com.rsmbs.simrs.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val displayFormat = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
    private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    fun today(): String = dateFormat.format(Date())

    fun now(): String = dateTimeFormat.format(Date())

    fun toDisplay(dateStr: String): String = try {
        displayFormat.format(dateFormat.parse(dateStr)!!)
    } catch (e: Exception) {
        dateStr
    }
}

object CurrencyUtils {
    fun format(amount: Long): String {
        val formatted = amount.toString().reversed().chunked(3).joinToString(".").reversed()
        return "Rp $formatted"
    }
}
