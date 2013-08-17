package no.nxc.bank.operation;

public class OperationResponse {
	private OperationResponseStatus status;
	private String message;
	
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
