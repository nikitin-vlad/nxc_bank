package org.common.transactions;

import java.util.ArrayList;

public class Transactions {
	private ArrayList<Transaction> data = new ArrayList<Transaction>();

	public ArrayList<Transaction> getData() {
		return data;
	}

	public void setData(ArrayList<Transaction> data) {
		this.data = data;
	}
	
	public Transaction getTransaction(String cardNumber) {
		return null;
	}
	
	public Transaction getTransaction(String cardNumber, String operationName) {
		return null;
	}
	
	public void addTransaction(Transaction transaction) {
		data.add(transaction);
	}
}
