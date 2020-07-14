package com.nikasov.waterbalance.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
//    fun getDateWithoutTimeUsingCalendar(date : Date): Date {
//        val calendar: Calendar = Calendar.getInstance()
//        calendar.set(Calendar.HOUR_OF_DAY, 0)
//        calendar.set(Calendar.MINUTE, 0)
//        calendar.set(Calendar.SECOND, 0)
//        calendar.set(Calendar.MILLISECOND, 0)
//        return calendar.time
//    }
    fun getDayByDate(date: Date): Date {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.parse(formatter.format(date))!!
    }
}