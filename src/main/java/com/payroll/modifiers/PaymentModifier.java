package com.payroll.modifiers;

import java.util.List;

public interface PaymentModifier {
	
	PaymentModifierType getModifierType();

	List<PaymentModifierItem> getModifiersItems();
	
	void addModifierItem(PaymentModifierItem modifierItem);

	double salaryModifier();
	
}
