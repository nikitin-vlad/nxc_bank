package org.common.atms;

import java.util.HashMap;

public class Atm {

	private String id;
	private Double balance;
	private boolean status;
	
	private HashMap<Integer, Integer> bills = new HashMap<Integer, Integer>();
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Double getBalance() {
		return balance;
	}
	
	public void setBalance(Double balance) {
		this.balance = balance;
	
	}
	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}

	public HashMap<Integer, Integer> getBills() {
		return bills;
	}

	public void setBills(HashMap<Integer, Integer> bills) {
		this.bills = bills;
	}
	
	
}
