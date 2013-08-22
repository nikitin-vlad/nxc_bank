package org.common.atms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.common.accounts.Account;
import org.common.conversion.MapBillsAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class Atm {

	private String id;
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
		Double balance = 0.0;
		for(Entry<Integer, Integer> billEntry : bills.entrySet()) {
		    int bill = billEntry.getKey();
		    int amount = billEntry.getValue();
		    balance += bill * amount;
		}
		return balance;
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
	
    @XmlJavaTypeAdapter(MapBillsAdapter.class)
	@XmlElement(name="bills")
	public void setBills(HashMap<Integer, Integer> bills) {
		this.bills = bills;
	}

	public Object[][] getBillsData() {
		
		final Object[][] result = new Object[bills.size()][2];
		final Iterator<?> iter = bills.entrySet().iterator();

		int i = 0;
		while(iter.hasNext()){
		    final Map.Entry<?, ?> mapping = (Map.Entry<?, ?>) iter.next();

		    result[i][0] = mapping.getKey();
		    result[i][1] = mapping.getValue();

		    i++;
		}

		return result;
	}
	
	public void addBill(int billName, int billAmount) {
		bills.put(billName, billAmount);
	}
	
	public String getCash(Account account, int money) {
		try {
			account.setAmount(account.getAmount() - money);
			
					
		} catch (Exception e) {
			account.setAmount(account.getAmount() + money);
			return "Please, try again later.";
		}
		return "Operation successfull. Dont forget money and you card! See you next time. Bye.";
	}
	
}
