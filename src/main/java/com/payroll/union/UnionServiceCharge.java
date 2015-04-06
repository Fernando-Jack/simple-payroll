package com.payroll.union;

import java.util.Date;

import lombok.Getter;

public class UnionServiceCharge {
	
	public UnionServiceCharge(int unionChargeID, int unionMember, Date date, double amount) {
		this.unionChargeID = unionChargeID;
		this.unionMemberID = unionMember;
		this.createdDate = date;
		this.chargeAmount = amount;
	}
	
	private @Getter int unionChargeID;
	private @Getter int unionMemberID;
	private @Getter Date createdDate;
	private @Getter double chargeAmount;


}
