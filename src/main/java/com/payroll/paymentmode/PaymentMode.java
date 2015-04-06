package com.payroll.paymentmode;

public interface PaymentMode {

	enum PaymentMethod {
		Bank, Paycheck, Postal
	}

	PaymentMethod getPaymentType();

	void pay(int employeeID, double employeSalary);
	

}