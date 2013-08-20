package org.common.operations;

public enum OperationResponseStatus {
	OK,
	WrongCredentials,
	CardNotEnoughCash,
	AtmNotEnoughCash,
	BanknotesAmmountError,
	ServerError,
	IncorrectRequest,
	NonRegisteredAtm
}
