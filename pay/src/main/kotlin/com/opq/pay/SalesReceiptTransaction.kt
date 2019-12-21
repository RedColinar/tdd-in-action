package com.opq.pay

import java.lang.RuntimeException
import java.util.*

/**
 * Created by panqiang at 2019-08-03
 */
class SalesReceiptTransaction(
    private val empId: Int,
    private val date: Date,
    private val amount: Double
) : Transaction {
    override fun execute() {
        val employee = getEmployee(empId)
        val paymentClassification = employee.paymentClassification
        if (paymentClassification is CommissionedClassification) {
            paymentClassification.addSalesReceipt(SalesReceipt(date, amount))
        } else {
            throw RuntimeException("add SalesReceipt to non-SalesReceipt employee")
        }
    }
}