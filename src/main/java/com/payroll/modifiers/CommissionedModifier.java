package com.payroll.modifiers;

import java.util.ArrayList;
import java.util.List;

public class CommissionedModifier implements PaymentModifier {

	private List<PaymentModifierItem> salesReceipts = new ArrayList<PaymentModifierItem>();
	private double commissionRate;

	public CommissionedModifier(double commissionRate) {
		this.commissionRate = commissionRate;
	}

	@Override
	public PaymentModifierType getModifierType() {
		return PaymentModifierType.Commission;
	}

	@Override
	public List<PaymentModifierItem> getModifiersItems() {
		return salesReceipts;
	}

	@Override
	public void addModifierItem(PaymentModifierItem modifierItem) {
		salesReceipts.add(modifierItem);
	}

	@Override
	public double salaryModifier() {
		double coeficient = 0.0;

		for (PaymentModifierItem item : salesReceipts)
			coeficient += item.getModifierCoeficient();

		return coeficient*commissionRate;
	}

}
