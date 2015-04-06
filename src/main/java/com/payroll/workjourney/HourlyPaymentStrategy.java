package com.payroll.workjourney;

import java.util.Collection;

import com.payroll.modifiers.PaymentModifier;
import com.payroll.modifiers.PaymentModifierType;

public class HourlyPaymentStrategy implements PaymentStrategy {

	private final double hourlyRate;

	public HourlyPaymentStrategy(double hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

	@Override
	public WorkJourneyType getJourneyType() {
		return WorkJourneyType.Hourly;
	}

	@Override
	public double getSalary(Collection<PaymentModifier> collection) {
		return applyModifiersOnSalary(collection);
	}

	private double applyModifiersOnSalary(Collection<PaymentModifier> collection) {

		double modifiedSalary = 0;
		
		for (PaymentModifier modifier : collection){
			if(modifier.getModifierType().equals(PaymentModifierType.HourlyPaid))			
				modifiedSalary = (hourlyRate * modifier.salaryModifier());
			
			if(modifier.getModifierType().equals(PaymentModifierType.UnionCharge))			
				modifiedSalary -= modifier.salaryModifier();
		}

		return modifiedSalary;
	}

}
