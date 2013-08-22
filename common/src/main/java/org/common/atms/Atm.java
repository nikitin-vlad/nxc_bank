package org.common.atms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.common.accounts.Account;
import org.common.conversion.MapBillsAdapter;
import org.common.operations.OperationType;
import org.common.transactions.Transaction;
import org.common.transactions.Transactions;

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
			int moneyOrig = money;
			account.setAmount(account.getAmount() - money);
			ArrayList<Integer> nominals = new ArrayList<Integer>();
			for ( int bill : bills.keySet() ) {
				nominals.add(bill);
			}
			if (nominals.isEmpty()) {
				return "Not enough money in ATM. Please, try again later.";
			}
			Collections.sort(nominals);
			HashMap<Integer, Integer> foundNominals = new HashMap<Integer, Integer>();
			for(int i = 0, l = nominals.size(); i < l; ++i) {
				int nominal = nominals.get(i);
				int billCountTotal = bills.get(nominal);
				int bill = billCountTotal * nominal;
				if (bill > money) continue;
				int billCount = 0;
				
				while(bill < money) { // ( <= )
					money -= bill;
					++billCount;
				}
				if (billCountTotal == billCount) {
					bills.remove(nominal);
				}
				foundNominals.put(nominal, billCount);
			}
			
			if (money > 0) {
				return "rest: "+money;
			}
			
			String nominalsText = "For "+moneyOrig+" you will get next nominals: ";
			
			for(int bill : foundNominals.keySet()){
				nominalsText += bill + " x " + foundNominals.get(bill) +"; ";
		    }
			
			Transactions transactions = new Transactions();
			Transaction transaction = new Transaction();
			
			transaction.setCardNumber(account.getCardNumber());
			transaction.setOperationName(OperationType.GetCash.toString());
			transaction.setCreated(new Date());
			
			transactions = transactions.getTransactions();
			transactions.addTransaction(transaction);
			
			return nominalsText;		
		} catch (Exception e) {
			account.setAmount(account.getAmount() + money);
			return "Please, try again later.";
		}
	}
	
}
