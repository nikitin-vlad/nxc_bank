package no.nxc.bank.operation;

public enum OperationResponseStatus {
	OK,
	WrongCredentials,
	CardNotEnoughCash,
	AtmNotEnoughCash,
	BanknotesAmmountError,
	ServerError,
	IncorrectRequest,
}
