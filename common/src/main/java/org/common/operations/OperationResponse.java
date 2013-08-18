package org.common.operations;

import java.io.Serializable;

public class OperationResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4495548719843239608L;
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
