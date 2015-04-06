package com.payroll.paymentmode;


public class PostalPaymentMode implements PaymentMode {

	@Override
	public PaymentMethod getPaymentType() {
		return PaymentMethod.Postal;
	}

	@Override
	public void pay(int employeeID, double employeSalary) {
		// TODO implement	
	}

}
