/**
 * 
 */
package com.payroll.workjourney;

import java.util.Collection;

import com.payroll.modifiers.PaymentModifier;

/**
 * @author jack
 *
 */
public interface PaymentStrategy {

	enum WorkJourneyType{
		Hourly, Monthly
	}
	
	WorkJourneyType getJourneyType();

	double getSalary(Collection<PaymentModifier> collection);	
	
}
