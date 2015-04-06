package com.payroll.paymentmode;


public class BankPaymentMode implements PaymentMode {

	@Override
	public PaymentMethod getPaymentType() {
		return PaymentMethod.Bank;
	}

	@Override
	public void pay(int employeeID, double employeSalary) {
		// TODO implement		
	}

}
