package com.payroll;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.payroll.modifiers.CommissionedModifier;
import com.payroll.modifiers.HourlyPaidModifier;
import com.payroll.modifiers.PaymentModifier;
import com.payroll.modifiers.UnionChargeModifier;
import com.payroll.paymentmode.FakePaymentServiceSystem;
import com.payroll.paymentmode.PaymentMode;
import com.payroll.paymentmode.PaymentServiceSystem;
import com.payroll.schedule.DefaultPaymentSchedule;
import com.payroll.schedule.PaymentSchedule;
import com.payroll.workjourney.HourlyPaymentStrategy;
import com.payroll.workjourney.MonthlyPaymentStrategy;
import com.payroll.workjourney.PaymentStrategy;

public class PayrollTestHelper {

	  static Employee makeHourlyPaidEmployeeForTest() {
		double hourlyRate = 20.0;
		PaymentStrategy journey = new HourlyPaymentStrategy(hourlyRate);
		PaymentModifier hourlyModifier = new HourlyPaidModifier();
		int employeeID = 0;

		Employee emp = new Employee(employeeID, "Fernando", "endereço", journey, PaymentMode.PaymentMethod.Paycheck);
		emp.addPaymentModifier(hourlyModifier);
		return emp;
	}
	
	  static Employee makeDefaultHourlyAndComissionedEmployeeForTest(PayrollSystem payroll) {
		double hourlyRate = 20.0;
		double commissionRate = 2.0;
		PaymentStrategy journey = new HourlyPaymentStrategy(hourlyRate);
		PaymentModifier comissionModifier = new CommissionedModifier(commissionRate);
		int employeeID = 0;

		Employee employee = new Employee(employeeID, "Fernando", "endereço", journey,
				PaymentMode.PaymentMethod.Paycheck);
		employee.addPaymentModifier(comissionModifier);

		payroll.addEmployee(employee);
		return employee;
	}
	
	  static Employee makeHourlyAndNotComissionedEmployeForTest() {
		double hourlyRate = 20.0;
		double unionCharge = 2.0;
		PaymentStrategy hourlyJourney = new HourlyPaymentStrategy(hourlyRate);
		PaymentModifier unionModifier = new UnionChargeModifier(unionCharge);
		int employeeID = 0;
		Employee emp = new Employee(employeeID, "Fernando", "endereço", hourlyJourney,
				PaymentMode.PaymentMethod.Paycheck);
		emp.addPaymentModifier(unionModifier);
		return emp;
	}
	
	  static Employee makeMonthlyEmployeForTest() {
		double salary = 2000.0;
		PaymentStrategy journey = new MonthlyPaymentStrategy(salary);
		int employeeID = 0;
		Employee employee = new Employee(employeeID, "Fernando", "endereço", journey,
				PaymentMode.PaymentMethod.Paycheck);
		return employee;
	}
	
	  static Date setPayrollSystemCurrentDateToValidPaymentDate(PayrollSystem payroll, Date bottomDate) {
		Date date = bottomDate;
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(date);
		cal.add(GregorianCalendar.MINUTE, 1);
		Date currentDatePlusOneMinute = cal.getTime();
		payroll.setCurrentDate(currentDatePlusOneMinute);
		return currentDatePlusOneMinute;
	}

	  static PayrollSystem makeDefaultPayrollSystemForTest() {
		PayrollSystem payroll = PayrollSystem.getInstance();
		PaymentSchedule payschedule = new DefaultPaymentSchedule();
		payroll.setPaymentSchedule(payschedule);
		PaymentServiceSystem paySystem = new FakePaymentServiceSystem();
		payroll.setPaymentSystem(paySystem);
		return payroll;
	}
	
	  static Double calculateHourlySalary(double hourlyRate, long... workedHours) {
		double salary = 0.0;
		double extraHourModifier = 0.5;
		long limiteHoursWorkedPerDay = 8;

		for (long hour : workedHours) {
			double extraHour = ((hour % limiteHoursWorkedPerDay) * extraHourModifier);
			salary += (hour + extraHour) * hourlyRate;
		}

		return new Double(salary);
	}

	
}
