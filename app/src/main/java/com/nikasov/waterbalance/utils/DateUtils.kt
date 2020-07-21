package com.nikasov.waterbalance.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getDayByDate(date: Date): Date {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.parse(formatter.format(date))!!
    }

    fun getFormattedDay(date: Date): String {
        val formatter = SimpleDateFormat("MMM d", Locale.getDefault())
        return formatter.format(date)
    }

    fun getMonth(date: Date): String {
        val formatter = SimpleDateFormat("MMM", Locale.getDefault())
        return formatter.format(date)
    }

    fun getDay(date: Date): String {
        val formatter = SimpleDateFormat("EEE", Locale.getDefault())
        return formatter.format(date)
    }
}