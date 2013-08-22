package org.server;

import org.common.accounts.Account;
import org.common.operations.OperationRequest;
import org.common.operations.OperationResponse;
import org.common.operations.OperationResponseStatus;

//TODO implement getcash case
public class Controller {
	
	public OperationResponse handleRequest(OperationRequest request) {
		Account account = Server.getAccounts().getAccount(request.getCardNumber(), request.getPass());
		if (account == null) {
			return new OperationResponse(OperationResponseStatus.WrongCredentials, "Invalid card number");
		}
		
		switch (request.getOperation())
		{
		case Balance:
			return new OperationResponse(OperationResponseStatus.OK, "Your card balanse is " + account.getAmount());
		//TODO implement getcash case
		case GetCash:
			return new OperationResponse(OperationResponseStatus.OK, "here you are sere ");
		case ReloadAmount:
			int money = Integer.parseInt(request.getData());
			account.setAmount(account.getAmount() + money);
			return new OperationResponse(OperationResponseStatus.OK, "Your card balanse was successfully changed, now is " + account.getAmount());
		case Transactions:
			
		}
		return new OperationResponse(OperationResponseStatus.OperationNotSupported, "Unknown operation");
	}
}
