package no.nxc.bank;

import java.io.IOException;
import java.util.Map;

import org.apache.sshd.SshServer;

class SshServerProvider {
	
	private SshServer server;
	private Map<String,String> authMap;
	
	public SshServerProvider (int port, Map<String,String> am) {
		this.authMap = am;
		this.server = SshServer.setUpDefaultServer();
		this.server.setPort(port);
		this.server.setPasswordAuthenticator(new UserPasswordAuthenticator(authMap));
	}
	
	public void start () throws IOException {
		this.server.start();
	}
	
}
