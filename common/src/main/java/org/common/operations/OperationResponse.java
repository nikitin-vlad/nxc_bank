package org.common.operations;

public class OperationResponse {
	private OperationResponseStatus status;
	private String message;
	
	public OperationResponse (OperationResponseStatus st, String mess) {
		this.status = st;
		this.message = mess;
	}
	
	public OperationResponseStatus getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setStatus(OperationResponseStatus status) {
		this.status = status;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
