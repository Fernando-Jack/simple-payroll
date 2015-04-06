package com.payroll.paymentmode;


public class PaycheckPaymentMode implements PaymentMode {

	@Override
	public PaymentMethod getPaymentType() {
		return PaymentMethod.Paycheck;
	}

	@Override
	public void pay(int employeeID, double employeSalary) {
		// TODO implement
	}	

}
