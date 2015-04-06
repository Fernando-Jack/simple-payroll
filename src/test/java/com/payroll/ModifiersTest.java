package com.payroll;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.payroll.modifiers.CommissionedModifier;
import com.payroll.modifiers.PaymentModifier;
import com.payroll.modifiers.PaymentModifierItem;
import com.payroll.modifiers.PaymentModifierType;
import com.payroll.modifiers.SaleReceipt;
import com.payroll.paymentmode.PaymentMode;
import com.payroll.workjourney.MonthlyPaymentStrategy;
import com.payroll.workjourney.PaymentStrategy;

public class ModifiersTest {	

	@Test
	public final void comissionedEmployee_ShouldBeAbleToAddSalesReceipts() {
		PayrollSystem payroll = PayrollSystem.getInstance();

		double monthlySalary = 20.0;
		double commissionRate = 2.0;

		PaymentStrategy hourlyJourney = new MonthlyPaymentStrategy(monthlySalary);		
		PaymentModifier modifier = new CommissionedModifier(commissionRate);

		Employee hourlyEmployee = new Employee(0, "Fernando", "endereço", hourlyJourney, PaymentMode.PaymentMethod.Paycheck);
		hourlyEmployee.addPaymentModifier(modifier);

		payroll.addEmployee(hourlyEmployee);
		
		int employeeID = 0;
		Date creationDate = new Date(System.currentTimeMillis());		
		
		SaleReceipt sr = payroll.createSalesReceiptForEmployee(employeeID, creationDate, 100);	
		SaleReceipt sr2 = payroll.createSalesReceiptForEmployee(employeeID, creationDate, 200);
		
		List<PaymentModifierItem> items = hourlyEmployee.getPaymentModifierItems(PaymentModifierType.Commission);
		
		assertTrue(sr.equals(items.get(0)));
		assertTrue(sr2.equals(items.get(1)));		
	}

}
