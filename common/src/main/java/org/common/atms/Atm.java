package org.common.atms;

import org.common.conversion.MapAdapter;

import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Atm {

	private String id;
	private Double balance;
	private boolean status;
	
	private HashMap<Integer, Integer> bills = new HashMap<Integer, Integer>();
	
	public String getId() {
		return id;
	}
	@XmlElement
	public void setId(String id) {
		this.id = id;
	}
	
	public Double getBalance() {
		return balance;
	}
	@XmlElement
	public void setBalance(Double balance) {
		this.balance = balance;
	
	}
	public boolean getStatus() {
		return status;
	}
	@XmlElement
	public void setStatus(boolean status) {
		this.status = status;
	}

	public HashMap<Integer, Integer> getBills() {
		return bills;
	}
    @XmlJavaTypeAdapter(MapAdapter.class)
	@XmlElement(name="bills")
	public void setBills(HashMap<Integer, Integer> bills) {
		this.bills = bills;
	}

	public Object[][] getBillsData() {
		Object[][] data = {
			{0, 0}
		};
		return data;
	}
	
	
}
