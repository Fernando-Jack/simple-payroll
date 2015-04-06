package com.payroll;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EmployeeTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public final void shouldAddEmployeeAndRecoverHer() {
		PayrollSystem payroll = PayrollSystem.getInstance();

		Employee employee = new Employee(0, "Carol", "endereço", null, null);
		payroll.addEmployee(employee);

		Employee addedEmployee = payroll.getEmployee(employee.getEmployeeID());

		assertTrue(employee.equals(addedEmployee));
	}

	@Test
	public final void shouldThrowExceptionWhenEmployeeHasNegativeID() {
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Employee parameters are not well structured. " + "Employe ID is negative. "
				+ "Employee ID [-1]");
		
		@SuppressWarnings("unused")
		Employee emp1 = new Employee(-1, "Fernando", "endereço", null, null);
	}

	@Test
	public final void shouldThrowExceptionWhenEmployeeHasEmptyParameters() {	

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Employee parameters are not well structured. " + "Some parameters are empty. ");
		
		@SuppressWarnings("unused")
		Employee emp1 = new Employee(0, "", "", null, null);
	}

	@Test
	public final void shouldAddEmployeeAndRemoveHim() {
		PayrollSystem payroll = PayrollSystem.getInstance();
		
		Employee emp = new Employee(0, "Fernando", "endereço", null, null);
		payroll.addEmployee(emp);

		payroll.removeEmployee(emp.getEmployeeID());

		assertTrue(payroll.getEmployee(emp.getEmployeeID()) == null);

	}

	@Test
	public final void shouldThrowExceptionWhenRemovedEmployeeNotExist() {

		PayrollSystem payroll = PayrollSystem.getInstance();

		Employee emp = new Employee(0, "Fernando", "endereço", null, null);
		
		payroll.addEmployee(emp);
		payroll.removeEmployee(emp.getEmployeeID());

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Employee does not exist. Employee ID [-1]");
		payroll.removeEmployee(-1);
		
	}

}
