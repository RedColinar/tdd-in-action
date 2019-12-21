package com.opq.pay

import java.util.*
import kotlin.collections.HashMap
import kotlin.math.max

interface PaymentClassification {
    fun calculatePay(payCheck: PayCheck): Double
}

class HourlyClassification(val hourlyRate: Double) : PaymentClassification {
    private val timeCards = HashMap<Date, TimeCard>()

    fun addTimeCard(timeCard: TimeCard) {
        timeCards[timeCard.date] = timeCard
    }

    fun getTimeCard(date: Date): TimeCard {
        return timeCards[date]!!
    }

    override fun calculatePay(payCheck: PayCheck): Double {
        var totalPay = 0.0
        for (timeCard in timeCards.values) {
            if (payCheck.isDateInPayPeriod(timeCard.date)) {
                totalPay += calculatePayForTimeCard(timeCard)
            }
        }
        return totalPay
    }

    private fun calculatePayForTimeCard(timeCard: TimeCard): Double {
        val hours = timeCard.hours
        val overtime = max(0.0, hours - 8.0)
        val straightTime = hours - overtime
        return straightTime * hourlyRate + overtime * hourlyRate * 1.5
    }
}

class SalariedClassification(val salary: Double) : PaymentClassification {
    override fun calculatePay(payCheck: PayCheck): Double {
        return salary
    }
}

class CommissionedClassification(
    val commissionRate: Double,
    val salary: Double
) : PaymentClassification {
    private val salesReceipts = HashMap<Date, SalesReceipt>()

    fun addSalesReceipt(salesReceipt: SalesReceipt) {
        salesReceipts[salesReceipt.date] = salesReceipt
    }

    fun getSalesReceipt(date: Date): SalesReceipt {
        return salesReceipts[date]!!
    }

    override fun calculatePay(payCheck: PayCheck): Double {
        var grossPay = salary
        for (saleReceipt in salesReceipts.values) {
            if (payCheck.isDateInPayPeriod(saleReceipt.date)) {
                grossPay += saleReceipt.amount * commissionRate
            }
        }
        return grossPay
    }
}