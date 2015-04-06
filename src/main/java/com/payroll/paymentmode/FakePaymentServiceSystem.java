package com.payroll.paymentmode;

import java.util.HashMap;
import java.util.Map;

import com.payroll.paymentmode.PaymentMode.PaymentMethod;

public class FakePaymentServiceSystem implements PaymentServiceSystem {

	private Map<PaymentMethod,PaymentMode> paymentsModes = new HashMap<PaymentMode.PaymentMethod, PaymentMode>();
	private StringBuilder paymentLog = new StringBuilder();
	
	{
		paymentsModes.put(PaymentMethod.Bank, new BankPaymentMode());
		paymentsModes.put(PaymentMethod.Paycheck, new PaycheckPaymentMode());
		paymentsModes.put(PaymentMethod.Postal, new PostalPaymentMode());
	}

	@Override
	public String getPaymentLog() {		
		return paymentLog.toString();
	}	

	@Override
	public void pay(PaymentMethod paymentType, int employeeID, double employeSalary) {
		paymentsModes.get(paymentType).pay(employeeID, employeSalary);
		paymentLog.append("Employee ID["+employeeID+"] was paid by ["+paymentType.toString().toUpperCase()+"];");
	}

}
