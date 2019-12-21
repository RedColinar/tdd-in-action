package com.opq.pay

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class EmployeeTest {
    @Test
    fun testAddSalariedEmployee() {
        val empId = 1
        AddSalariedEmployee(empId, "Bob", "Home", 1000.0).execute()

        val employee = getEmployee(empId)
        assert("Bob" == employee.name)

        val paymentClassification = employee.paymentClassification
        assert(paymentClassification is SalariedClassification)
        paymentClassification as SalariedClassification
        assertEquals(1000.0, paymentClassification.salary, 0.0)

        val paymentSchedule = employee.paymentSchedule
        assert(paymentSchedule is MonthlySchedule)

        val paymentMethod = employee.paymentMethod
        assert(paymentMethod is HoldMethod)
    }

    @Test
    fun testAddHourlyEmployee() {
        val empId = 2
        AddHourlyEmployee(empId, "Tom", "Home", 15.0).execute()

        val employee = getEmployee(empId)

        val paymentClassification = employee.paymentClassification
        assert(paymentClassification is HourlyClassification)
        paymentClassification as HourlyClassification
        assertEquals(15.00, paymentClassification.hourlyRate, 0.0)

        val paymentSchedule = employee.paymentSchedule
        assert(paymentSchedule is WeeklySchedule)

        val paymentMethod = employee.paymentMethod
        assert(paymentMethod is HoldMethod)
    }

    @Test
    fun testAddCommissionedEmployee() {
        val empId = 3
        AddCommissionedEmployee(empId, "Sean", "Home", 500.0, 100.0).execute()

        val employee = getEmployee(empId)

        val paymentClassification = employee.paymentClassification
        assert(paymentClassification is CommissionedClassification)
        paymentClassification as CommissionedClassification
        assertEquals(500.00, paymentClassification.salary, 0.0)
        assertEquals(100.00, paymentClassification.commissionRate, 0.0)

        val paymentSchedule = employee.paymentSchedule
        assert(paymentSchedule is BiweeklySchedule)

        val paymentMethod = employee.paymentMethod
        assert(paymentMethod is HoldMethod)
    }

    @Test
    fun testDeleteEmployee() {
        val empId = 4
        AddCommissionedEmployee(empId, "Harry", "Home", 500.0, 100.0).execute()
        DeleteEmployeeTransaction(empId).execute()
        assert(!containsEmployee(empId))
    }

    @Test
    fun testTimeCardTransaction() {
        val empId = 5
        val date = Date(2019, 8, 3)
        assertEquals(Date(2019, 8, 3), Date(2019, 8, 3))

        AddHourlyEmployee(empId, "Bill", "Home", 15.25).execute()
        TimeCardTransaction(empId, date, 8.0).execute()

        val employee = getEmployee(empId)
        val paymentClassification = employee.paymentClassification
        if (paymentClassification is HourlyClassification) {
            val timeCard = paymentClassification.getTimeCard(date)
            assert(timeCard.hours == 8.0)
        }
    }

    @Test
    fun testSalesReceiptTransaction() {
        val empId = 6
        val date = Date(2019, 8, 3)
        AddCommissionedEmployee(empId, "Bill", " Home", 500.0, 3.2).execute()
        SalesReceiptTransaction(empId, date, 200.0).execute()

        val employee = getEmployee(empId)
        val paymentClassification = employee.paymentClassification
        if (paymentClassification is CommissionedClassification) {
            val salesReceipt = paymentClassification.getSalesReceipt(date)
            assert(salesReceipt.amount == 200.0)
        }
    }

    @Test
    fun testAddServiceCharge() {
        val empId = 7
        val memberId = 86
        val date = Date(2019, 8, 3)
        AddHourlyEmployee(empId, "Bill", "Home", 15.25).execute()
        val employee = getEmployee(empId)
        val unionAffiliation = UnionAffiliation(memberId, 12.5)
        employee.addAffiliation(unionAffiliation)
        addAffiliationMember(memberId, employee)

        ServiceChargeTransaction(memberId, date, 12.95).execute()
        val serviceCharge = unionAffiliation.getServiceCharge(date)
        assertEquals(12.95, serviceCharge.amount, 0.0)
    }

    @Test
    fun testChangeHourlyTransaction() {
        val empId = 8
        AddCommissionedEmployee(empId, "Bill", "Home", 2500.0, 3.2).execute()
        ChangeEmployeeToHourlyTransaction(empId, 27.52).execute()

        val employee = getEmployee(empId)
        val paymentClassification = employee.paymentClassification
        assert(paymentClassification is HourlyClassification)

        paymentClassification as HourlyClassification
        assertEquals(27.52, paymentClassification.hourlyRate, 0.0)
        val paymentSchedule = employee.paymentSchedule
        assert(paymentSchedule is WeeklySchedule)
    }

    @Test
    fun testChangeCommissionedTransaction() {
        val empId = 9
        AddHourlyEmployee(empId, "Bill", "Home", 15.32).execute()
        ChangeEmployeeToCommissionedTransaction(empId, 3.2, 2500.0).execute()

        val employee = getEmployee(empId)
        val paymentClassification = employee.paymentClassification
        assert(paymentClassification is CommissionedClassification)

        paymentClassification as CommissionedClassification
        assertEquals(2500.0, paymentClassification.salary, 0.0)
        val paymentSchedule = employee.paymentSchedule
        assert(paymentSchedule is MonthlySchedule)
    }

    @Test
    fun testChangeSalariedTransaction() {
        val empId = 10
        AddHourlyEmployee(empId, "Bill", "Home", 15.32).execute()
        ChangeEmployeeToSalariedTransaction(empId, 5500.0).execute()

        val employee = getEmployee(empId)
        val paymentClassification = employee.paymentClassification
        assert(paymentClassification is SalariedClassification)

        paymentClassification as SalariedClassification
        assertEquals(5500.0, paymentClassification.salary, 0.0)
        val paymentSchedule = employee.paymentSchedule
        assert(paymentSchedule is MonthlySchedule)
    }

    @Test
    fun testChangeAffiliationTransaction() {
        val empId = 11
        val memberId = 4396

        AddHourlyEmployee(empId, "Bill", "Home", 15.25).execute()
        AddAffiliationTransaction(empId, memberId, 99.42).execute()

        val employee = getEmployee(empId)
        val affiliation = employee.getAffiliation(memberId) as UnionAffiliation
        assertEquals(99.42, affiliation.dues, 0.0)
        assertEquals(employee, getAffiliationMember(memberId))

        EraseAffiliationTransaction(empId, memberId).execute()
        assert(!containsAffiliationMember(memberId))
    }
}
