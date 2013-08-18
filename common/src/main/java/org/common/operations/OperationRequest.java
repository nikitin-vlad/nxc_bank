package org.common.operations;

public class OperationRequest {
	private String cardNumber;
	private int pass;
	private OperationType operation;
	private String data;
	
	public OperationRequest() {	}
	
	public OperationRequest(String cardNumber, int pass, OperationType operation) {
		this.cardNumber = cardNumber;
		this.pass = pass;
		this.operation = operation;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public int getPass() {
		return pass;
	}

	public OperationType getOperation() {
		return operation;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public void setPass(int pass) {
		this.pass = pass;
	}

	public void setOperation(OperationType operation) {
		this.operation = operation;
	}
	
	
}
