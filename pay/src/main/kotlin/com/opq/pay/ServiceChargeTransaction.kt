package com.opq.pay

import java.util.*

/**
 * Created by panqiang at 2019-08-03
 */
class ServiceChargeTransaction(
    private val memberId: Int,
    private val date: Date,
    private val charge: Double
) : Transaction {
    override fun execute() {
        val employee = getAffiliationMember(memberId)
        val affiliation = employee.getAffiliation(memberId)

        if (affiliation is UnionAffiliation) {
            affiliation.addServiceCharge(ServiceCharge(date, charge))
        }
    }
}