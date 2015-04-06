package com.payroll;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import com.payroll.modifiers.PaymentModifierType;
import com.payroll.modifiers.SaleReceipt;
import com.payroll.modifiers.TimeCard;
import com.payroll.paymentmode.PaymentServiceSystem;
import com.payroll.schedule.PaymentSchedule;
import com.payroll.workjourney.PaymentStrategy;

public class PayrollSystem {
	
	public static synchronized PayrollSystem getInstance(){
		return new PayrollSystem();
	}
	
	public void addEmployee(Employee emp) {
		employees.put(emp.getEmployeeID(), emp);
	}

	public Employee getEmployee(int employeeID) {		
		return employees.get(employeeID);		
	}

	public void removeEmployee(int employeeID) {
		Object returned = employees.remove(employeeID);
		
		if(returned == null)
			throw new IllegalArgumentException("Employee does not exist. Employee ID ["+employeeID+"]");
	}

	public TimeCard createTimeCardForEmployee(int employeeID, Date date, long hours) {
		
		Employee emp = getEmployee(employeeID);
		
		if( ! emp.workJourney().equals(PaymentStrategy.WorkJourneyType.Hourly) )
			throw new IllegalArgumentException("It is only allowed to create timecard for Hourly Employ");
		
		TimeCard tm = new TimeCard(employeeID, date, hours);
		emp.addPaymentModifierItem(PaymentModifierType.HourlyPaid, tm);
		
		return tm;
		
	}

	public SaleReceipt createSalesReceiptForEmployee(int employeeID, Date date, double amount) {
		Employee emp = getEmployee(employeeID);
		
		if(emp.acceptPaymentModifier(PaymentModifierType.Commission)){
			SaleReceipt receipt = new SaleReceipt(employeeID, date, amount);
			emp.addPaymentModifierItem(PaymentModifierType.Commission, receipt);
			return receipt;
		}
		
		throw new IllegalArgumentException("It is not allowed to create sales receipt for Not Commisioned Employee");
	}

	public void pay() {		
		for(Employee emp : employees.values())
			if(shouldPayEmployeeNow(emp))
				payEmployee(emp);
		
	}	

	private void payEmployee(Employee emp) {		
			emp.setLastPaymentDate(getCurrentDate());
			paymentSystem.pay(emp.getPaymentMethod(), emp.getEmployeeID(), emp.getSalary());
			paymentLog.append("Employee ID["+emp.getEmployeeID()+"] was paid on time ["+getCurrentDate().toString()+"]");
	}
	
	private boolean shouldPayEmployeeNow(Employee emp) {
		return paymentSchedule.shouldPayEmployeeNow(emp.getWorkJourneyType(), currentDate);
	}
	
	public String getPaymentLog() {		
		return paymentLog.toString();
	}
	

	public void clearPaymentLog() {
		paymentLog = new StringBuilder();
	}	
	
	public String getPaymentServiceLog() {
		return paymentSystem.getPaymentLog();
	}
	
	private @Setter PaymentSchedule paymentSchedule;
	private @Getter @Setter PaymentServiceSystem paymentSystem;
	private @Getter @Setter Date currentDate;
	private Map<Integer, Employee> employees = new HashMap<Integer,Employee>();
	private StringBuilder paymentLog = new StringBuilder();
	
	private PayrollSystem(){
		
	}

	

}
