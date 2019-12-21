package com.opq.pay

import java.util.*

data class PayCheck(
    val startDate: Date,
    val endDate: Date,
    var grossPay: Double = 0.0,
    var deductions: Double = 0.0,
    var netPay: Double = 0.0
) {
    fun isDateInPayPeriod(date: Date): Boolean {
        // [startDate, endDate)
        return (date.after(startDate) || date == startDate) && date.before(endDate)
    }
}