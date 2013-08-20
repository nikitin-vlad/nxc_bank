package org.server;

import org.common.accounts.Account;
import org.common.atms.Atm;
import org.common.operations.OperationRequest;
import org.common.operations.OperationResponse;
import org.common.operations.OperationResponseStatus;
//TODO implement getcash case
public class Controller {
	private ClientHandler clientHandler;
	
	public Controller(ClientHandler ch) {
		this.clientHandler = ch;
	}
	
	public OperationResponse handleRequest(OperationRequest request) {
		Account account = Server.getAccounts().getAccount(request.getCardNumber());
		if (account == null) {
			return new OperationResponse(OperationResponseStatus.WrongCredentials, "Invalid card number");
		}
		
		if ( !account.getPassword().equals(String.valueOf(request.getPass())) ) {
			return new OperationResponse(OperationResponseStatus.WrongCredentials, "Invalid password");
		}
		
		Atm atm = Server.getAtms().getAtm(this.clientHandler.getClientName());
		if (atm == null) {
			return new OperationResponse(OperationResponseStatus.NonRegisteredAtm, "this atm is'nt registered, contact administrator");
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
		}
		return new OperationResponse(OperationResponseStatus.OK, "");
	}
}
