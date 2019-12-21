package com.opq.pay

import java.util.*
import kotlin.collections.HashMap

interface Affiliation {
    fun calculateDeductions(payCheck: PayCheck): Double
}

class UnionAffiliation(val memberId: Int, val dues: Double) : Affiliation {

    private val serviceCharges = HashMap<Date, ServiceCharge>()

    fun addServiceCharge(serviceCharge: ServiceCharge) {
        serviceCharges[serviceCharge.date] = serviceCharge
    }

    fun getServiceCharge(date: Date): ServiceCharge {
        return serviceCharges[date]!!
    }

    override fun calculateDeductions(payCheck: PayCheck): Double {
        val totalDues = dues * numberOfFridaysInPayPeriod(payCheck.startDate, payCheck.endDate)
        var totalServiceCharge = 0.0
        for (serviceCharge in serviceCharges.values) {
            if (payCheck.isDateInPayPeriod(serviceCharge.date)) {
                totalServiceCharge += serviceCharge.amount
            }
        }
        return totalDues + totalServiceCharge
    }

    private fun numberOfFridaysInPayPeriod(startDate: Date, endDate: Date): Int {
        var fridays = 0
        val calendar = Calendar.getInstance()
        calendar.time = startDate
        while (calendar.time.before(endDate)) {
            if (calendar.time.day == 5) {
                fridays++
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        return fridays
    }
}