package com.payroll;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.payroll.union.UnionMember;
import com.payroll.union.UnionServiceCharge;
import com.payroll.union.FakeUnionStorage;

public class UnionTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public final void shouldCreateServiceUnionChargeAndAddToUnionMember() {
		FakeUnionStorage fakeUnionStorage = new FakeUnionStorage();
		fakeUnionStorage.addMember(new UnionMember(0, "Fernando"));

		int unionMemberID = 0;
		Date date = new Date(System.currentTimeMillis());
		double amount = 100.0;

		UnionServiceCharge chargeAdded = fakeUnionStorage.createServiceUnionCharge(unionMemberID, date, amount);
		
		UnionMember member = fakeUnionStorage.getUnionMember(unionMemberID);
		List<UnionServiceCharge> charges = member.getUnionCharges();

		assertTrue(chargeAdded.equals(charges.get(0)));
	}

	@Test
	public final void shouldThrowExceptionWhenCreateUnionChargeAndUnionMemberNotExist() {
		FakeUnionStorage fakeUnionStorage = new FakeUnionStorage();

		int unionMemberID = -1;
		Date date = new Date(System.currentTimeMillis());
		double amount = 100.0;

		exception.expect(IllegalStateException.class);
		exception.expectMessage("Union member not exist");
		@SuppressWarnings("unused")
		UnionServiceCharge unionServiceCharge = fakeUnionStorage.createServiceUnionCharge(unionMemberID, date, amount);
	}
	
	@Test
	public final void shouldAddEmployeToUnion(){
		FakeUnionStorage fakeUnionStorage = new FakeUnionStorage();
		
		UnionMember member = new UnionMember(0, "Fernando");
		fakeUnionStorage.addMember(member);		
		UnionMember added = fakeUnionStorage.getUnionMember(0);
		
		assertTrue(member.equals(added));	
	}
	
	@Test
	public final void shouldRemoveEmployeFromUnion(){
		FakeUnionStorage fakeUnionStorage = new FakeUnionStorage();
		
		UnionMember member = new UnionMember(0, "Fernando");
		fakeUnionStorage.addMember(member);		
		fakeUnionStorage.removeUnionMember(0);
		
		assertTrue(fakeUnionStorage.getUnionMember(0) == null);	
	}

}
