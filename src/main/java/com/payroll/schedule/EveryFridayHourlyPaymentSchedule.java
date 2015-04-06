package com.payroll.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EveryFridayHourlyPaymentSchedule {
	
	public static boolean shouldPayEmployeeNow(Date currentDate) {		
		if( currentDate.before(getCurrentUpperPaymentDate()) && currentDate.after(getCurrentBottomPaymentDate()) )		
			return true;
		
		return false;
	}
	
	public static Date getCurrentUpperPaymentDate(){
		Calendar pCal = Calendar.getInstance();
		pCal.set(GregorianCalendar.DAY_OF_WEEK,Calendar.FRIDAY);	
		pCal.set(GregorianCalendar.HOUR_OF_DAY, 23);
		pCal.set(GregorianCalendar.MINUTE, 0);
		pCal.set(GregorianCalendar.SECOND, 0);
		Date date  = pCal.getTime();
		return date;
	}
	
	public static Date getCurrentBottomPaymentDate(){
		Calendar pCal = Calendar.getInstance();
		pCal.set(GregorianCalendar.DAY_OF_WEEK,Calendar.FRIDAY);	
		pCal.set(GregorianCalendar.HOUR_OF_DAY, 0);
		pCal.set(GregorianCalendar.MINUTE, 0);
		pCal.set(GregorianCalendar.SECOND, 0);
		Date date  = pCal.getTime();
		return date;
	}
}
