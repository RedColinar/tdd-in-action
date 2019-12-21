package com.opq.pay

import java.util.*

interface PaymentSchedule {
    fun isPayDay(payDay: Date): Boolean
    fun getPayStartDate(payDay: Date): Date
}

class WeeklySchedule : PaymentSchedule {
    override fun getPayStartDate(payDay: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = payDay
        calendar.add(Calendar.WEEK_OF_YEAR, -1)
        return calendar.time
    }

    override fun isPayDay(payDay: Date): Boolean {
        return payDay.day == 5
    }
}

class MonthlySchedule : PaymentSchedule {
    override fun getPayStartDate(payDay: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = payDay
        calendar.add(Calendar.MONTH, -1)
        return calendar.time
    }

    override fun isPayDay(payDay: Date): Boolean {
        return isLastDayOfMonth(payDay)
    }

    private fun isLastDayOfMonth(payDay: Date): Boolean {
        val nextDay = Calendar.getInstance()
        nextDay.time = payDay
        nextDay.add(Calendar.DAY_OF_MONTH, 1)
        return nextDay.get(Calendar.MONTH) > payDay.month
    }
}

class BiweeklySchedule : PaymentSchedule {
    override fun getPayStartDate(payDay: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = payDay
        calendar.add(Calendar.WEEK_OF_YEAR, -2)
        return calendar.time
    }

    override fun isPayDay(payDay: Date): Boolean {
        return payDay.day == 5
    }
}