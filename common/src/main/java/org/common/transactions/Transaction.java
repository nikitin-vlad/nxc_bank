package org.common.transactions;

import java.util.Date;

public class Transaction {

	private String cardNumber;
	private String operationName;
	private String operationData;
	private Date created;
	private String atmId;
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getOperationName() {
		return operationName;
	}
	
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
	public String getOperationData() {
		return operationData;
	}
	
	public void setOperationData(String operationData) {
		this.operationData = operationData;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public String getAtmId() {
		return atmId;
	}
	
	public void setAtmId(String atmId) {
		this.atmId = atmId;
	}
	
}
