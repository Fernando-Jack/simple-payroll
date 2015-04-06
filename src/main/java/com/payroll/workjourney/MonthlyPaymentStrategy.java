package com.payroll.workjourney;

import java.util.Collection;

import com.payroll.modifiers.PaymentModifier;
import com.payroll.modifiers.PaymentModifierType;

public class MonthlyPaymentStrategy implements PaymentStrategy {

	private double monthlySalary;

	public MonthlyPaymentStrategy(double monthlySalary) {
		this.monthlySalary = monthlySalary;
	}

	@Override
	public WorkJourneyType getJourneyType() {
		return WorkJourneyType.Monthly;
	}
	
	@Override
	public double getSalary(Collection<PaymentModifier> collection) {
		return applyModifiers(collection);
	}

	private double applyModifiers(Collection<PaymentModifier> collection) {

		double modifiedSalary = monthlySalary;

		for (PaymentModifier modifier : collection){
			if(modifier.getModifierType().equals(PaymentModifierType.Commission))			
				modifiedSalary += modifier.salaryModifier();
			
			if(modifier.getModifierType().equals(PaymentModifierType.UnionCharge))			
				modifiedSalary -= modifier.salaryModifier();
		}

		return modifiedSalary;
	}

}
