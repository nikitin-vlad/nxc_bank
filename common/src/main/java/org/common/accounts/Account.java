package org.common.accounts;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Account {
	
	private String cardNumber;
	private String password;
	private boolean status;
	private Double amount;
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	@XmlElement
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getPassword() {
		return password;
	}
	
	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isStatus() {
		return status;
	}
	
	@XmlElement
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	@XmlElement
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
