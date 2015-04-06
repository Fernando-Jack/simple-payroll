package com.payroll.paymentmode;

import com.payroll.paymentmode.PaymentMode.PaymentMethod;

public interface PaymentServiceSystem {

	String getPaymentLog();

	void pay(PaymentMethod paymentType, int employeeID, double employeSalary);

}
