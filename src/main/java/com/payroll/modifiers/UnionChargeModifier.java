package com.payroll.modifiers;

import java.util.Collections;
import java.util.List;

public class UnionChargeModifier implements PaymentModifier {

	private List<PaymentModifierItem> unionCharges;
	private double unionCharge;

	public UnionChargeModifier(double unionCharge) {
		this.unionCharge = unionCharge;
	}

	@Override
	public PaymentModifierType getModifierType() {
		return PaymentModifierType.UnionCharge;
	}

	@Override
	public List<PaymentModifierItem> getModifiersItems() {
		return (List<PaymentModifierItem>) Collections.unmodifiableCollection(unionCharges);
	}

	@Override
	public void addModifierItem(PaymentModifierItem modifierItem) {
		unionCharges.add(modifierItem);		
	}

	@Override
	public double salaryModifier() {
		// TODO implement union logic
		return unionCharge;
	}

}
