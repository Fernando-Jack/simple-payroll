package com.payroll.union;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class FakeUnionStorage {	

	public void addMember(UnionMember unionMember) {
		unionMembers.add(unionMember);		
	}

	public UnionServiceCharge createServiceUnionCharge(int unionMemberID, Date date, double amount) {
		
		boolean exist = unionMemberExist(unionMemberID);	
		
		if(exist){
			int unionServiceChargeIdentifier = generateNewUnionChargeIdentifier();
			UnionServiceCharge charge = new UnionServiceCharge(unionServiceChargeIdentifier, unionMemberID,date,amount);
			getUnionMember(unionMemberID).addUnionCharge(charge);
			return charge;
		}
		
		throw new IllegalStateException("Union member not exist");
	}

	private int generateNewUnionChargeIdentifier() {		
		return internalUnionCount++;				
	}

	public UnionMember getUnionMember(int unionMemberID) {
		for(UnionMember um : unionMembers )
			if(um.getUnionMemberID() == unionMemberID)
				return um;
		
		return null;
		
	}

	private boolean unionMemberExist(int unionMemberID) {
		for(UnionMember um : unionMembers )
			if(um.getUnionMemberID() == unionMemberID)
				return true;
		
		return false;
	}

	public void removeUnionMember(int unionMemberID) {
		for(UnionMember um : unionMembers )
			if(um.getUnionMemberID() == unionMemberID)
				unionMembers.remove(um);
	}
	
	private Set<UnionMember> unionMembers = new HashSet<UnionMember>();
	private int internalUnionCount;

}
