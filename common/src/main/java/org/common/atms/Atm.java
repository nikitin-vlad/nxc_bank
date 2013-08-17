package org.common.atms;

import java.util.HashMap;

public class Atm {

	private String id;
	private String sslKeyMark;
	private Double balance;
	private double status;
	
	//private HashMap<int, int> bills = new HashMap<int, int>();
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getSslKeyMark() {
		return sslKeyMark;
	}
	
	public void setSslKeyMark(String sslKeyMark) {
		this.sslKeyMark = sslKeyMark;
	}
	
	public Double getBalance() {
		return balance;
	}
	
	public void setBalance(Double balance) {
		this.balance = balance;
	
	}
	public double getStatus() {
		return status;
	}
	
	public void setStatus(double status) {
		this.status = status;
	}
	
	
}
