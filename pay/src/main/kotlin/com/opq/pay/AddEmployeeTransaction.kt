package com.opq.pay

abstract class AddEmployeeTransaction(
    open val empId: Int,
    open val name:String,
    open val address: String
) : Transaction {

    abstract fun getClassification() : PaymentClassification

    abstract fun getSchedule() : PaymentSchedule

    override fun execute() {
        val employee = Employee(empId, name, address)
        employee.paymentClassification = getClassification()
        employee.paymentSchedule = getSchedule()
        employee.paymentMethod = HoldMethod()
        addEmployee(employee)
    }
}

class AddSalariedEmployee(
    override val empId: Int,
    override val name:String,
    override val address: String,
    private val salary: Double
) : AddEmployeeTransaction(empId, name, address) {

    override fun getClassification(): PaymentClassification = SalariedClassification(salary)

    override fun getSchedule(): PaymentSchedule = MonthlySchedule()
}

class AddHourlyEmployee(
    override val empId: Int,
    override val name:String,
    override val address: String,
    private val hourlyRate: Double
) : AddEmployeeTransaction(empId, name, address) {

    override fun getClassification(): PaymentClassification = HourlyClassification(hourlyRate)

    override fun getSchedule(): PaymentSchedule = WeeklySchedule()
}

class AddCommissionedEmployee(
    override val empId: Int,
    override val name:String,
    override val address: String,
    private val salary: Double,
    private val commissionRate: Double
) : AddEmployeeTransaction(empId, name, address) {

    override fun getClassification(): PaymentClassification = CommissionedClassification(commissionRate, salary)

    override fun getSchedule(): PaymentSchedule = BiweeklySchedule()
}