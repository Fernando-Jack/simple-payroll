package com.payroll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.payroll.modifiers.CommissionedModifier;
import com.payroll.modifiers.HourlyPaidModifier;
import com.payroll.modifiers.PaymentModifier;
import com.payroll.modifiers.SaleReceipt;
import com.payroll.modifiers.TimeCard;
import com.payroll.paymentmode.PaymentMode;
import com.payroll.schedule.EveryFridayHourlyPaymentSchedule;
import com.payroll.schedule.LastFridayMonthlyPaymentSchedule;
import com.payroll.workjourney.HourlyPaymentStrategy;
import com.payroll.workjourney.MonthlyPaymentStrategy;
import com.payroll.workjourney.PaymentStrategy;

public class PayrollTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public final void shouldCreateTimeCard() {
		PayrollSystem payroll = PayrollSystem.getInstance();
		Employee employee = PayrollTestHelper.makeHourlyPaidEmployeeForTest();
		payroll.addEmployee(employee);

		Date currentDate = new Date(System.currentTimeMillis());
		long workedHours = 8;
		TimeCard timeCard = payroll.createTimeCardForEmployee(employee.getEmployeeID(), currentDate, workedHours);

		assertTrue(timeCard.getEmployeeID() == employee.getEmployeeID());
		assertTrue(timeCard.getCreatedDate().equals(currentDate));
		assertTrue(timeCard.getWorkedHours() == workedHours);
	}

	@Test
	public final void shouldThrowException_WhenTimeCardEmployeeIsNotHourly() {

		PayrollSystem payroll = PayrollSystem.getInstance();

		Employee emp = PayrollTestHelper.makeMonthlyEmployeForTest();
		payroll.addEmployee(emp);

		Date date = new Date(System.currentTimeMillis());
		long hours = 8;

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("It is only allowed to create timecard for Hourly Employ");
		payroll.createTimeCardForEmployee(emp.getEmployeeID(), date, hours);
	}

	@Test
	public final void shouldCreateSalesReceipt() {

		PayrollSystem payroll = PayrollSystem.getInstance();
		Employee employee = PayrollTestHelper.makeDefaultHourlyAndComissionedEmployeeForTest(payroll);

		Date currentDate = new Date(System.currentTimeMillis());
		double salesAmount = 100.0;

		SaleReceipt saleReceipt = payroll.createSalesReceiptForEmployee(employee.getEmployeeID(), currentDate,
				salesAmount);

		assertTrue(saleReceipt.getEmployeeID() == employee.getEmployeeID());
		assertTrue(saleReceipt.getCreatedDate().equals(currentDate));
		assertTrue(saleReceipt.getSaleAmount() == salesAmount);

	}

	@Test
	public final void shouldThrowException_WhenSaleReceiptEmployeeIsNotComissioned() {

		PayrollSystem payroll = PayrollSystem.getInstance();
		Employee employee = PayrollTestHelper.makeHourlyAndNotComissionedEmployeForTest();
		payroll.addEmployee(employee);

		Date date = new Date(System.currentTimeMillis());
		double amount = 100.0;

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("It is not allowed to create sales receipt for Not Commisioned Employee");
		payroll.createSalesReceiptForEmployee(employee.getEmployeeID(), date, amount);
	}

	@Test
	public final void shouldPayMonthlyPaidEmployees_WithSpecifiedMethod() {

		PayrollSystem payroll = PayrollTestHelper.makeDefaultPayrollSystemForTest();
		PayrollTestHelper.setPayrollSystemCurrentDateToValidPaymentDate(payroll,
				LastFridayMonthlyPaymentSchedule.getCurrentBottomPaymentDate());

		PaymentStrategy monthlyJourney = new MonthlyPaymentStrategy(2000);		

		Employee paycheckEmployee = new Employee(0, "Fernando", "endereço", monthlyJourney,
				PaymentMode.PaymentMethod.Paycheck);
		Employee bankEmployee = new Employee(1, "Fernando", "endereço", monthlyJourney, PaymentMode.PaymentMethod.Bank);
		Employee postalEmployee = new Employee(2, "Fernando", "endereço", monthlyJourney,
				PaymentMode.PaymentMethod.Postal);

		payroll.addEmployee(paycheckEmployee);
		payroll.addEmployee(bankEmployee);
		payroll.addEmployee(postalEmployee);
		payroll.pay();

		String expectedLogMessage = "Employee ID[0] was paid by [PAYCHECK];"
				+ "Employee ID[1] was paid by [BANK];"
				+ "Employee ID[2] was paid by [POSTAL];";
		
		String currentLogMessage = payroll.getPaymentServiceLog();

		assertEquals(expectedLogMessage, currentLogMessage);
	}
	
	@Test
	public final void shouldPayHourlyPaidEmployees_WithSpecifiedMethod() {

		PayrollSystem payroll = PayrollTestHelper.makeDefaultPayrollSystemForTest();
		PayrollTestHelper.setPayrollSystemCurrentDateToValidPaymentDate(payroll,
				EveryFridayHourlyPaymentSchedule.getCurrentBottomPaymentDate());

		PaymentStrategy hourlyJourney = new HourlyPaymentStrategy(20.0);		

		Employee paycheckEmployee = new Employee(0, "Foo", "endereço", hourlyJourney,
				PaymentMode.PaymentMethod.Paycheck);
		Employee bankEmployee = new Employee(1, "Boo", "endereço", hourlyJourney, PaymentMode.PaymentMethod.Bank);
		Employee postalEmployee = new Employee(2, "Bar", "endereço", hourlyJourney,
				PaymentMode.PaymentMethod.Postal);

		payroll.addEmployee(paycheckEmployee);
		payroll.addEmployee(bankEmployee);
		payroll.addEmployee(postalEmployee);
		payroll.pay();

		String expectedLogMessage = "Employee ID[0] was paid by [PAYCHECK];"
				+ "Employee ID[1] was paid by [BANK];"
				+ "Employee ID[2] was paid by [POSTAL];";
		
		String currentLogMessage = payroll.getPaymentServiceLog();

		assertEquals(expectedLogMessage, currentLogMessage);
	}
	

	@Test
	public final void lastPaymentDate_shouldBeChangedWhenEmployeeIsPaid() {

		PayrollSystem payroll = PayrollTestHelper.makeDefaultPayrollSystemForTest();
		PayrollTestHelper.setPayrollSystemCurrentDateToValidPaymentDate(payroll,
				LastFridayMonthlyPaymentSchedule.getCurrentBottomPaymentDate());

		Employee emp = PayrollTestHelper.makeMonthlyEmployeForTest();
		payroll.addEmployee(emp);

		payroll.pay();

		assertTrue(emp.getLastPaymentDate() != null);
	}

	@Test
	public final void shouldPayMonthlyEmployeeOnRightTime() {

		PayrollSystem payroll = PayrollTestHelper.makeDefaultPayrollSystemForTest();
		Date datePlusOneMinute = PayrollTestHelper.setPayrollSystemCurrentDateToValidPaymentDate(payroll,
				LastFridayMonthlyPaymentSchedule.getCurrentBottomPaymentDate());

		Employee employee = PayrollTestHelper.makeMonthlyEmployeForTest();

		payroll.addEmployee(employee);
		payroll.pay();

		String expectedPaymentMessage = "Employee ID[" + employee.getEmployeeID() + "] was paid on time ["
				+ datePlusOneMinute.toString() + "]";
		String currentPaymentLogMessage = payroll.getPaymentLog();

		assertEquals(expectedPaymentMessage, currentPaymentLogMessage);
		assertEquals(datePlusOneMinute, employee.getLastPaymentDate());
	}

	@Test
	public final void shouldPayHourlyEmployeeOnRightTime() {

		PayrollSystem payroll = PayrollTestHelper.makeDefaultPayrollSystemForTest();
		Date currentDatePlusOneMinute = PayrollTestHelper.setPayrollSystemCurrentDateToValidPaymentDate(payroll,
				EveryFridayHourlyPaymentSchedule.getCurrentBottomPaymentDate());

		Employee employee = PayrollTestHelper.makeHourlyPaidEmployeeForTest();

		payroll.addEmployee(employee);
		payroll.clearPaymentLog();
		payroll.pay();

		assertEquals("Employee ID["+employee.getEmployeeID()+"] was paid on time [" + currentDatePlusOneMinute.toString() + "]",
				payroll.getPaymentLog());
		assertEquals(currentDatePlusOneMinute, employee.getLastPaymentDate());
	}

	@Test
	public final void shouldPayRigthSalaryForMonthlyEmployee() {

		PayrollSystem payroll = PayrollTestHelper.makeDefaultPayrollSystemForTest();
		PayrollTestHelper.setPayrollSystemCurrentDateToValidPaymentDate(payroll,
				LastFridayMonthlyPaymentSchedule.getCurrentBottomPaymentDate());

		double salary = 2000.0;
		PaymentStrategy journey = new MonthlyPaymentStrategy(salary);
		Employee monthlyEmployee = new Employee(0, "Fernando", "endereço", journey, PaymentMode.PaymentMethod.Paycheck);

		payroll.addEmployee(monthlyEmployee);
		payroll.pay();

		Double expectedSalary = new Double(salary);
		Double currentSalary = monthlyEmployee.getSalary();

		assertEquals(expectedSalary, currentSalary);
	}

	@Test
	public final void shouldPayRigthSalaryForHourlyEmployee() {

		PayrollSystem payroll = PayrollTestHelper.makeDefaultPayrollSystemForTest();
		PayrollTestHelper.setPayrollSystemCurrentDateToValidPaymentDate(payroll, new Date());

		double hourlyRate = 2.0;
		PaymentStrategy payStrategy = new HourlyPaymentStrategy(hourlyRate);
		PaymentModifier hourlyModifier = new HourlyPaidModifier();
		Employee hourlyEmployee = new Employee(0, "Fernando", "endereço", payStrategy,
				PaymentMode.PaymentMethod.Paycheck);

		hourlyEmployee.addPaymentModifier(hourlyModifier);
		payroll.addEmployee(hourlyEmployee);

		int employeeID = 0;
		Date timeCardCreated = new Date(System.currentTimeMillis());
		long hours = 8;

		payroll.createTimeCardForEmployee(employeeID, timeCardCreated, hours);
		payroll.createTimeCardForEmployee(employeeID, timeCardCreated, hours);
		payroll.pay();

		Double expectedSalary = new Double(32.0);
		Double currentSalary = new Double(hourlyEmployee.getSalary());

		assertEquals(expectedSalary, currentSalary);
	}

	@Test
	public final void shouldAddCommisionToEmployeeSalary() {

		PayrollSystem payroll = PayrollTestHelper.makeDefaultPayrollSystemForTest();
		PayrollTestHelper.setPayrollSystemCurrentDateToValidPaymentDate(payroll,
				EveryFridayHourlyPaymentSchedule.getCurrentBottomPaymentDate());

		double hourlyRate = 2000.0;
		PaymentStrategy payStrategy = new MonthlyPaymentStrategy(hourlyRate);
		double commissionRate = 0.01;
		PaymentModifier comissionModifier = new CommissionedModifier(commissionRate);

		Employee comissionEmployee = new Employee(0, "Fernando", "endereço", payStrategy,
				PaymentMode.PaymentMethod.Paycheck);
		comissionEmployee.addPaymentModifier(comissionModifier);

		payroll.addEmployee(comissionEmployee);

		int employeeID = 0;
		Date timeCardCreated = new Date(System.currentTimeMillis());
		long amount = 300;

		payroll.createSalesReceiptForEmployee(employeeID, timeCardCreated, amount);
		payroll.pay();

		Double expectedSalary = new Double(2003.0);
		Double currentSalary = new Double(comissionEmployee.getSalary());

		assertEquals(expectedSalary, currentSalary);
	}

	@Test
	public final void shouldPayExtraHourForEmployee() {

		PayrollSystem payroll = PayrollTestHelper.makeDefaultPayrollSystemForTest();
		PayrollTestHelper.setPayrollSystemCurrentDateToValidPaymentDate(payroll, new Date());

		double hourlyRate = 2.0;
		PaymentStrategy payStrategy = new HourlyPaymentStrategy(hourlyRate);
		PaymentModifier hourlyModifier = new HourlyPaidModifier();
		Employee hourlyEmployee = new Employee(0, "Fernando", "endereço", payStrategy,
				PaymentMode.PaymentMethod.Paycheck);

		hourlyEmployee.addPaymentModifier(hourlyModifier);
		payroll.addEmployee(hourlyEmployee);

		int employeeID = 0;
		Date timeCardCreated = new Date(System.currentTimeMillis());
		long workedHours = 8;
		long workedHoursWithOneExtraHour = 9;
		long workedHoursWithOneHourLessThanTheLimite = 7;

		payroll.createTimeCardForEmployee(employeeID, timeCardCreated, workedHours);
		payroll.createTimeCardForEmployee(employeeID, timeCardCreated, workedHoursWithOneExtraHour);
		payroll.createTimeCardForEmployee(employeeID, timeCardCreated, workedHoursWithOneHourLessThanTheLimite);

		payroll.pay();

		Double currentSalary = new Double(hourlyEmployee.getSalary());
		Double expectedSalary = PayrollTestHelper.calculateHourlySalary(hourlyRate, workedHours,
				workedHoursWithOneExtraHour, workedHoursWithOneHourLessThanTheLimite);

		assertEquals(expectedSalary, currentSalary);
	}

}
