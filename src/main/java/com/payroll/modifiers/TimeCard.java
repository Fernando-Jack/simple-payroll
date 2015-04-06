package com.payroll.modifiers;

import java.util.Date;

import lombok.Getter;

public class TimeCard implements PaymentModifierItem {

	public TimeCard(int employeeID, Date date, long hours) {
		this.employeeID = employeeID;
		this.createdDate = date;
		this.workedHours = hours;
	}

	@Override
	public double getModifierCoeficient() {
		return workedHours;
	}
	

	private @Getter int employeeID;
	private @Getter Date createdDate;
	private @Getter long workedHours;


}
