package com.opq.pay

interface PaymentMethod {
    fun pay(payCheck: PayCheck)
}

class MailMethod : PaymentMethod {
    override fun pay(payCheck: PayCheck) {

    }
}

class HoldMethod : PaymentMethod {
    override fun pay(payCheck: PayCheck) {

    }
}

class DirectMethod : PaymentMethod {
    override fun pay(payCheck: PayCheck) {

    }
}