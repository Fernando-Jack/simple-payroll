package com.payroll.schedule;

import java.util.Date;

import com.payroll.workjourney.PaymentStrategy.WorkJourneyType;

public class DefaultPaymentSchedule implements PaymentSchedule {
	
	@Override
	public boolean shouldPayEmployeeNow(WorkJourneyType journeyType, Date currentDate) {
		
		if(journeyType.equals(WorkJourneyType.Monthly))
			return LastFridayMonthlyPaymentSchedule.shouldPayEmployeeNow(currentDate);
		
		if(journeyType.equals(WorkJourneyType.Hourly))
			return EveryFridayHourlyPaymentSchedule.shouldPayEmployeeNow(currentDate);
		
		return false;
	}

	

}
