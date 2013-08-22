package org.common.transactions;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction {

	private String cardNumber;
	private String operationName;
	private String operationData;
	private Date created;
	private String atmId;
	
	public String getCardNumber() {
		return cardNumber;
	}
	@XmlElement
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getOperationName() {
		return operationName;
	}
	@XmlElement
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
	public String getOperationData() {
		return operationData;
	}
	@XmlElement
	public void setOperationData(String operationData) {
		this.operationData = operationData;
	}
	
	public Date getCreated() {
		return created;
	}
	@XmlElement
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public String getAtmId() {
		return atmId;
	}
	@XmlElement
	public void setAtmId(String atmId) {
		this.atmId = atmId;
	}
	
}
