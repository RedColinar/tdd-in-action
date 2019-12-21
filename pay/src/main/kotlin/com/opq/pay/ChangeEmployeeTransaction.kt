package com.opq.pay

/**
 * Created by panqiang at 2019-08-03
 */
abstract class ChangeEmployeeTransaction(
    open val empId: Int
) : Transaction {

    override fun execute() {
        val employee = getEmployee(empId)
        change(employee)
        addEmployee(employee)
    }

    abstract fun change(employee: Employee)
}

abstract class ChangeClassificationTransaction(
    override val empId: Int
) : ChangeEmployeeTransaction(empId) {

    override fun change(employee: Employee) {
        employee.paymentClassification = getClassification()
        employee.paymentSchedule = getSchedule()
    }

    abstract fun getClassification(): PaymentClassification

    abstract fun getSchedule(): PaymentSchedule
}

class ChangeEmployeeToHourlyTransaction(
    override val empId: Int,
    private val hourlyRate: Double
) : ChangeClassificationTransaction(empId) {

    override fun getClassification(): PaymentClassification = HourlyClassification(hourlyRate)

    override fun getSchedule(): PaymentSchedule = WeeklySchedule()
}

class ChangeEmployeeToSalariedTransaction(
    override val empId: Int,
    private val salary: Double
) : ChangeClassificationTransaction(empId) {

    override fun getClassification(): PaymentClassification = SalariedClassification(salary)

    override fun getSchedule(): PaymentSchedule = MonthlySchedule()
}

class ChangeEmployeeToCommissionedTransaction(
    override val empId: Int,
    private val commissionRate: Double,
    private val salary: Double
) : ChangeClassificationTransaction(empId) {

    override fun getClassification(): PaymentClassification =
        CommissionedClassification(commissionRate, salary)

    override fun getSchedule(): PaymentSchedule = MonthlySchedule()
}

class AddAffiliationTransaction(
    override val empId: Int,
    private val memberId: Int,
    private val dues: Double
) : ChangeEmployeeTransaction(empId) {

    override fun change(employee: Employee) {
        employee.addAffiliation(UnionAffiliation(memberId, dues))
        // 这个类知道了数据库的方法，不令人满意
        addAffiliationMember(memberId, employee)
    }
}

class EraseAffiliationTransaction(
    override val empId: Int,
    private val memberId: Int
) : ChangeEmployeeTransaction(empId) {

    override fun change(employee: Employee) {
        employee.removeAffiliation(memberId)
        // 这个类知道了数据库的方法，不令人满意
        eraseAffiliationMember(memberId)
    }
}