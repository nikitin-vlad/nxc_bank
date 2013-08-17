package org.server.operation;

public enum OperationResponseStatus {
	OK,
	WrongCredentials,
	CardNotEnoughCash,
	AtmNotEnoughCash,
	BanknotesAmmountError,
	ServerError,
	IncorrectRequest,
}
