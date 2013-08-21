package org.common.transactions;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.common.atms.Atm;
import org.common.atms.Atms;
import org.common.conversion.UnmarshallAtms;
import org.common.conversion.UnmarshallTransactions;


public class Transactions {

	private ArrayList<Transaction> data = new ArrayList<Transaction>();
	
	public Transactions() {
		Transactions transaction = UnmarshallTransactions.unMarshall();
		data.addAll((Collection<? extends Transaction>) transaction);
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
