package com.payroll.union;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class UnionMember {		
	
	public UnionMember(int unionMemberID, String name) {
		this.unionMemberID = unionMemberID;
		this.name = name;
	}

	public void addUnionCharge(UnionServiceCharge charge) {
		unionServiceCharges.add(charge);
	}

	public List<UnionServiceCharge> getUnionCharges() {
		return Collections.unmodifiableList(unionServiceCharges);
	}
	
	private @Getter int unionMemberID;
	private @Getter String name;
	private List<UnionServiceCharge> unionServiceCharges = new ArrayList<UnionServiceCharge>();

}
