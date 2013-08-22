package org.common.transactions;

import java.util.ArrayList;

import org.common.configs.Config;
import org.common.conversion.XMLConversion;


public class Transactions {

	private ArrayList<Transaction> data = new ArrayList<Transaction>();
    private static String path = "common/target/resources" + Config.transactionsFile;
	
	public Transactions() {
        Transactions transactions = XMLConversion.unMarshall(Transactions.class, path);
	}

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
