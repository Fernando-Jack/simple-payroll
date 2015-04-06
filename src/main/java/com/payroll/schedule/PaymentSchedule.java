package com.payroll.schedule;

import java.util.Date;

import com.payroll.workjourney.PaymentStrategy.WorkJourneyType;

public interface PaymentSchedule {

	boolean shouldPayEmployeeNow(WorkJourneyType journeyType, Date currentDate);

}
