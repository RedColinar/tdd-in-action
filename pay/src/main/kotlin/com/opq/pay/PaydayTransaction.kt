package com.opq.pay

import java.util.*
import kotlin.collections.HashMap

class PaydayTransaction(
    private val payDay: Date
) : Transaction {

    private val payCheckMap = HashMap<Int, PayCheck>()

    override fun execute() {
        val employees = getAllEmployees()
        for (employee in employees) {
            if (employee.isPayDay(payDay)) {
                val payCheck = PayCheck(employee.getPayStartDate(payDay), payDay)
                employee.payDay(payCheck)
                payCheckMap[employee.id] = payCheck
            }
        }
    }

    fun getPayCheck(empId: Int): PayCheck? {
        return payCheckMap[empId]
    }
}