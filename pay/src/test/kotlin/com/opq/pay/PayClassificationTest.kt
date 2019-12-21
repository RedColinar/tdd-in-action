package com.opq.pay

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class PayClassificationTest {

    // region 钟点工
    @Test
    fun testPayNoTimeCard() {
        val empId = 1
        AddHourlyEmployee(empId, "Bill", "Home", 15.25).execute()
        // 周五
        val payDate = Date(2001, 9, 11)
        val paydayTransaction = PaydayTransaction(payDate)
        paydayTransaction.execute()
        validatePayCheck(paydayTransaction, empId, payDate, 0.0)
    }

    private fun validatePayCheck(
        paydayTransaction: PaydayTransaction,
        empId: Int,
        payDay: Date,
        pay: Double
    ) {
        val payCheck = paydayTransaction.getPayCheck(empId)!!
        assert(payCheck.endDate == payDay)
        assertEquals(pay, payCheck.grossPay, 0.0)
        assertEquals(0.0, payCheck.deductions, 0.0)
        assertEquals(pay, payCheck.netPay, 0.0)

        println("endDate:$payDay")
        println("grossPay:${payCheck.grossPay}")
        println("deductions:${payCheck.deductions}")
        println("netPay:${payCheck.netPay}")
    }

    @Test
    fun testPayOneTimeCard() {
        val empId = 1
        AddHourlyEmployee(empId, "Bill", "Home", 15.25).execute()
        val payDate = Date(2001, 9, 11)

        TimeCardTransaction(empId, Date(2001, 9, 10), 2.0).execute()

        val paydayTransaction = PaydayTransaction(payDate)
        paydayTransaction.execute()
        validatePayCheck(paydayTransaction, empId, payDate, 30.5)
    }

    @Test
    fun testPayOneTimeCardOverTime() {
        val empId = 1
        AddHourlyEmployee(empId, "Bill", "Home", 15.25).execute()
        val payDate = Date(2001, 9, 11)

        TimeCardTransaction(empId, Date(2001, 9, 10), 9.0).execute()

        val paydayTransaction = PaydayTransaction(payDate)
        paydayTransaction.execute()
        validatePayCheck(paydayTransaction, empId, payDate, (8 + 1.5) * 15.25)
    }

    @Test
    fun testPayOneTimeCardWrongDate() {
        val empId = 1
        AddHourlyEmployee(empId, "Bill", "Home", 15.25).execute()
        // 周六
        val payDate = Date(2001, 9, 12)

        TimeCardTransaction(empId, Date(2001, 9, 10), 9.0).execute()

        val paydayTransaction = PaydayTransaction(payDate)
        paydayTransaction.execute()
        assert(paydayTransaction.getPayCheck(empId) == null)
    }

    @Test
    fun testPayTwoTimeCard() {
        val empId = 1
        AddHourlyEmployee(empId, "Bill", "Home", 15.25).execute()
        // 周五
        val payDate = Date(2001, 9, 11)

        TimeCardTransaction(empId, Date(2001, 9, 9), 9.0).execute()
        TimeCardTransaction(empId, Date(2001, 9, 10), 2.0).execute()

        val paydayTransaction = PaydayTransaction(payDate)
        paydayTransaction.execute()
        validatePayCheck(paydayTransaction, empId, payDate, 15.25 * (2 + 8 + 1.5))
    }

    @Test
    fun testPayTimeCardInPeriod() {
        val empId = 1
        AddHourlyEmployee(empId, "Bill", "Home", 15.25).execute()
        // 周五
        val payDate = Date(2001, 9, 11)

        TimeCardTransaction(empId, Date(2001, 9, 6), 2.0).execute()
        TimeCardTransaction(empId, payDate, 8.0).execute()
        TimeCardTransaction(empId, Date(2001, 9, 12), 2.0).execute()

        val paydayTransaction = PaydayTransaction(payDate)
        paydayTransaction.execute()
        validatePayCheck(paydayTransaction, empId, payDate, 15.25 * 2)
    }
    // endregion

    // region 月薪
    @Test
    fun testPayDay() {
        val empId = 1
        AddSalariedEmployee(empId, "Bill", "Home", 1500.0).execute()

        val payDate = Date(2001, 9, 31)
        val paydayTransaction = PaydayTransaction(payDate)
        paydayTransaction.execute()

        validatePayCheck(paydayTransaction, empId, payDate, 1500.0)
    }

    @Test
    fun testPayDayWrongDate() {
        val empId = 1
        AddSalariedEmployee(empId, "Bill", "Home", 1500.0).execute()

        val payDate = Date(2001, 9, 30)
        val paydayTransaction = PaydayTransaction(payDate)
        paydayTransaction.execute()

        assert(paydayTransaction.getPayCheck(empId) == null)
    }
    // endregion
}