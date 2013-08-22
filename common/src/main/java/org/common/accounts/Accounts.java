package org.common.accounts;

import java.util.List;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static ch.lambdaj.Lambda.*;

import org.common.configs.Config;
import org.common.conversion.XMLConversion;
import org.hamcrest.Matchers;

@XmlRootElement
public class Accounts {
	@XmlElement(name = "account")
	private ArrayList<Account> data = new ArrayList<Account>();
	private boolean blocked = false;
    private static String path = "common/target/resources" + Config.accountsFile;

//	public Accounts() {
//			for (int i = 0, l = 5; i < l; i++) {
//				Account acc = new Account();
//				acc.setAmount(100.00);
//				acc.setCardNumber("1000000000000"+(i+1));
//				acc.setPassword("pass"+i);
//				acc.setStatus(true);
//				data.add(acc);
//			}
//	}
	
	public void clear() {
		data.clear();
	}
	public ArrayList<Account> init(){
		Accounts acc = XMLConversion.unMarshall(Accounts.class, path);
		int i = 1;
		try{
			while(acc.getAccount(i) != null){
				data.add(acc.getAccount(i));
				i++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.getMessage();			
		}

		return data;
	}
	
	public Account getAccount(String cardNumber) {
		List<Account> accounts = filter(having(on(Account.class).getCardNumber(), Matchers.equalTo(cardNumber)), data);
		Object[] values = accounts.toArray();
		return (values.length > 0) ? (Account) values[0] : null;
	}
	
	public void addAccount(Account account) {
		this.init();
		data.add(account);
		XMLConversion.marshall(this, path);
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public int count() {
		return data.size();
	}

	public Account getAccount(int index) {
		Object[] values = data.toArray();
		if (values.length > 0 && ( (index-1) <= values.length && (index-1) >= 0) ) {
			return (Account) values[index-1];
		}
		return null;
	}

	public void removeAccount(String cardNumber) {
		this.init();
		List<Account> acountList = filter(having(on(Account.class).getCardNumber(), Matchers.equalTo(cardNumber)), data);
		if (!acountList.isEmpty()) {
			Account account = acountList.get(0);
			data.remove(account);
		}
		XMLConversion.marshall(this, path);
	}

	public boolean isExisting(String cardNumber) {
		List<Account> acountList = filter(having(on(Account.class).getCardNumber(), Matchers.equalTo(cardNumber)), data);
		if (acountList.isEmpty()){
			return false;
		}
		return true;
	}
}
