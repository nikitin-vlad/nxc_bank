package org.server;

import javax.net.ssl.SSLSocket;

public class ClientHandlerFactory {
	public ClientHandler create (SSLSocket s, String a) {
		return new ClientHandler(s, a);
	}
}
