package com.payroll.modifiers;

import java.util.Date;

import lombok.Getter;

public class SaleReceipt implements PaymentModifierItem {

	public SaleReceipt(int employeeID, Date date, double amount) {
		this.employeeID = employeeID;
		this.createdDate = date;
		this.saleAmount = amount;
	}

	@Override
	public double getModifierCoeficient() {
		return saleAmount;
	}
	
	private @Getter Date createdDate;
	private @Getter int employeeID;
	private @Getter double saleAmount;

}
