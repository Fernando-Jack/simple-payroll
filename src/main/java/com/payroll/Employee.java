package com.payroll;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import com.payroll.modifiers.PaymentModifier;
import com.payroll.modifiers.PaymentModifierItem;
import com.payroll.modifiers.PaymentModifierType;
import com.payroll.paymentmode.PaymentMode.PaymentMethod;
import com.payroll.workjourney.PaymentStrategy;
import com.payroll.workjourney.PaymentStrategy.WorkJourneyType;

public class Employee {	

	public Employee(int employeeID, String name, String address, PaymentStrategy paymentStrategy, PaymentMethod paymentMethod) {
		
		this.employeeID = employeeID;
		this.name = name;
		this.address = address;
		this.paymentStrategy = paymentStrategy;
		this.paymentMethod = paymentMethod;
		

		if(name.isEmpty() || address.isEmpty())
			throw new IllegalArgumentException("Employee parameters are not well structured. "
					+ "Some parameters are empty. " 
					+ toString());
		
		if(employeeID < 0)
			throw new IllegalArgumentException("Employee parameters are not well structured. "
					+ "Employe ID is negative. "
					+ "Employee ID ["+employeeID+"]");
		
		
	}		

	public PaymentStrategy.WorkJourneyType workJourney() {		
		return paymentStrategy.getJourneyType();
	}

	public void addPaymentModifier(PaymentModifier modifier) {
		paymentModifiers.put(modifier.getModifierType(),modifier);		
	}

	public Set<PaymentModifier> getPaymentModifiers() {
		return Collections.unmodifiableSet( new HashSet<PaymentModifier>(paymentModifiers.values()));		
	}		

	public double getSalary() {
		return paymentStrategy.getSalary( paymentModifiers.values() );
	}

	public boolean acceptPaymentModifier(PaymentModifierType modifierType) {
		for(PaymentModifier modifier : getPaymentModifiers())
			if(modifierType.equals(modifier.getModifierType()))
				return true;
		
		return false;
	}

	public void addPaymentModifierItem(PaymentModifierType modifier, PaymentModifierItem modifierItem) {		
		paymentModifiers.get(modifier).addModifierItem(modifierItem);
	}

	public List<PaymentModifierItem> getPaymentModifierItems(PaymentModifierType modifierType) {
		
		List<PaymentModifierItem> items = null;
		
		for(PaymentModifier modifier : getPaymentModifiers())
			if(modifierType.equals(modifier.getModifierType()))
				items = modifier.getModifiersItems();
		
		return items;
	}

	public WorkJourneyType getWorkJourneyType() {
		return paymentStrategy.getJourneyType();
	}
	
	private @Getter int employeeID;
	private @Getter @Setter String name;
	private @Getter @Setter String address;
	private @Getter @Setter Date lastPaymentDate;
	private @Getter @Setter PaymentMethod paymentMethod;
	private PaymentStrategy paymentStrategy;	
	private Map<PaymentModifierType,PaymentModifier> paymentModifiers = new HashMap<PaymentModifierType,PaymentModifier>();
		
	

}
