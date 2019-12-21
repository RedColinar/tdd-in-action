package com.opq.pay

private val employeeMap = HashMap<Int, Employee>()

fun getEmployee(empId: Int): Employee {
    return employeeMap[empId]!!
}

fun addEmployee(employee: Employee) {
    employeeMap[employee.id] = employee
}

fun deleteEmployee(empId: Int) {
    employeeMap.remove(empId)
}

fun containsEmployee(empId: Int): Boolean {
    return employeeMap.contains(empId)
}

fun getAllEmployees() : List<Employee> {
    return employeeMap.values.toList()
}

private val affiliationMap = HashMap<Int, Employee>()

fun addAffiliationMember(memberId: Int, employee: Employee) {
    affiliationMap[memberId] = employee
}

fun eraseAffiliationMember(memberId: Int) {
    affiliationMap.remove(memberId)
}

fun getAffiliationMember(memberId: Int): Employee {
    return affiliationMap[memberId]!!
}

fun containsAffiliationMember(memberId: Int): Boolean {
    return affiliationMap.containsKey(memberId)
}