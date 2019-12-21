package com.opq.pay

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*

class ServiceChargeTest {
    @Test
    fun testHourlyUnionMemberServiceCharge() {
        val empId = 1
        AddHourlyEmployee(empId, "Bill", "Home", 15.25).execute()
        val memberId = 4396
        AddAffiliationTransaction(empId, memberId, 9.42).execute()
        val payDate = Date(2001, 9, 11)
        val beforePayDate = Date(2001, 9, 10)
        ServiceChargeTransaction(memberId, beforePayDate, 19.42).execute()
        TimeCardTransaction(empId, beforePayDate, 8.0).execute()
        val pt = PaydayTransaction(payDate)
        pt.execute()
        val payCheck = pt.getPayCheck(empId)!!

        assert(payCheck.endDate == payDate)
        assertEquals(8 * 15.25, payCheck.grossPay, 0.0)
        assertEquals(9.42 + 19.42, payCheck.deductions, 0.0)
        assertEquals(payCheck.grossPay - payCheck.deductions, payCheck.netPay, 0.0)
    }
}