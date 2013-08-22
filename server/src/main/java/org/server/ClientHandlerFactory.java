package org.server;

import javax.net.ssl.SSLSocket;

import org.common.atms.Atm;

public class ClientHandlerFactory {
	public ClientHandler create (SSLSocket s, Atm a) {
		return new ClientHandler(s, a);
	}
}
