package no.nxc.bank.server;

import javax.net.ssl.SSLSocket;

public class ClientHandlerFactory {
	public ClientHandler create (SSLSocket s) {
		return new ClientHandler(s);
	}
}
