package org.common.accounts;

import java.util.HashMap;

public class Accounts {

	private HashMap<String, Account> data = new HashMap<String, Account>();
	
	public Account getAccount(String cardNumber) {
		return null;
	}
	
	public void addAccount(Account account) {
		data.put(account.getCardNumber(), account);
	}
}
