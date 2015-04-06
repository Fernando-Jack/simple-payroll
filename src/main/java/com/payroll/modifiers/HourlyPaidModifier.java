package com.payroll.modifiers;

import java.util.ArrayList;
import java.util.List;

public class HourlyPaidModifier implements PaymentModifier {

	private List<PaymentModifierItem> paymentModifiers = new ArrayList<PaymentModifierItem>();

	@Override
	public PaymentModifierType getModifierType() {
		return PaymentModifierType.HourlyPaid;
	}

	@Override
	public List<PaymentModifierItem> getModifiersItems() {
		return paymentModifiers ;
	}

	@Override
	public void addModifierItem(PaymentModifierItem modifierItem) {
		paymentModifiers.add(modifierItem);
	}

	@Override
	public double salaryModifier() {
		double workedHoursWithExtra = 0.0;
		double limiteWorkedHours = 8;
		double extraHoursModifier = 0.5;
		
		for(PaymentModifierItem item : paymentModifiers){
			double extraHour = ( (item.getModifierCoeficient() % limiteWorkedHours) * extraHoursModifier);
			workedHoursWithExtra += item.getModifierCoeficient() + extraHour;
		}
		
		return workedHoursWithExtra;
	}

}
