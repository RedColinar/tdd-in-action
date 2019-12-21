package com.opq.pay

import java.util.*
import kotlin.collections.HashMap

data class Employee(
    val id: Int,
    val name: String,
    val address: String
) {
    lateinit var paymentMethod: PaymentMethod
    lateinit var paymentClassification: PaymentClassification
    lateinit var paymentSchedule: PaymentSchedule

    val affiliations = HashMap<Int, Affiliation>()

    fun addAffiliation(affiliation: Affiliation) {
        affiliation as UnionAffiliation
        affiliations[affiliation.memberId] = affiliation
    }

    fun getAffiliation(memberId: Int): Affiliation {
        return affiliations[memberId]!!
    }

    fun removeAffiliation(memberId: Int) {
        affiliations.remove(memberId)
    }

    fun isPayDay(payDay: Date): Boolean {
        return paymentSchedule.isPayDay(payDay)
    }

    fun payDay(payCheck: PayCheck) {
        val grossPay = paymentClassification.calculatePay(payCheck)
        var deductions = 0.0
        for (affiliation in affiliations.values) {
            deductions += affiliation.calculateDeductions(payCheck)
        }
        val netPay = grossPay - deductions
        payCheck.grossPay = grossPay
        payCheck.deductions = deductions
        payCheck.netPay = netPay
        paymentMethod.pay(payCheck)
    }

    fun getPayStartDate(payDay: Date): Date {
        return paymentSchedule.getPayStartDate(payDay)
    }
}