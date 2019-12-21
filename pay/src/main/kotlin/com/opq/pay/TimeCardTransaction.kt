package com.opq.pay

import java.lang.RuntimeException
import java.util.*

/**
 * Created by panqiang at 2019-08-03
 */
class TimeCardTransaction(
    val empId: Int,
    val date: Date,
    val hours: Double
) : Transaction {
    override fun execute() {
        val employee = getEmployee(empId)
        val paymentClassification = employee.paymentClassification

        if (paymentClassification is HourlyClassification) {
            paymentClassification.addTimeCard(TimeCard(date, hours))
        } else {
            throw RuntimeException("add time card to non-hourly employee")
        }
    }
}