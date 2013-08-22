package org.server;

import org.common.accounts.Account;
import org.common.atms.Atm;
import org.common.operations.OperationRequest;
import org.common.operations.OperationResponse;
import org.common.operations.OperationResponseStatus;

public class Controller {
	public Atm atm;
	public void setAtm (Atm name) {
		atm = name;
	}
	
	public OperationResponse handleRequest(OperationRequest request) {
		Account account = Server.getAccounts().getAccount(request.getCardNumber(), request.getPass());
		if (account == null) {
			return new OperationResponse(OperationResponseStatus.WrongCredentials, "Invalid card number");
		}
		if (atm == null) {
			return new OperationResponse(OperationResponseStatus.WrongAtm, "You are operating on broken ATM! Find another one please.");
		}
		
		int money;
		
		switch (request.getOperation())
		{
		case Balance:
			return new OperationResponse(OperationResponseStatus.OK, account.getAmount() + "");
		case GetCash:
			money = Integer.parseInt(request.getData());
			if (money > account.getAmount()) {
				return new OperationResponse(OperationResponseStatus.CardNotEnoughCash, "Wrong amount, try another amount!");
			} else if (money > atm.getBalance()) {
				return new OperationResponse(OperationResponseStatus.AtmNotEnoughCash, "Too big amount for current atm, try another amount!");
			} else {
				return new OperationResponse(OperationResponseStatus.OK, atm.getCash(account, money));
			}
		case ReloadAmount:
			money = Integer.parseInt(request.getData());
			account.setAmount(account.getAmount() + money);
			return new OperationResponse(OperationResponseStatus.OK, "Your card balance was successfully changed, your current balance: " + account.getAmount());
		case Transactions:
			
		}
		return new OperationResponse(OperationResponseStatus.OperationNotSupported, "Unknown operation");
	}
}
