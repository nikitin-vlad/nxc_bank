package no.nxc.bank;

import java.util.Map;

import org.apache.sshd.server.PasswordAuthenticator;
import org.apache.sshd.server.session.ServerSession;

class UserPasswordAuthenticator implements PasswordAuthenticator {
	private Map<String,String> authMap;
	
	public UserPasswordAuthenticator (Map<String,String> am) {
		this.authMap = am;
	}
	
	public boolean authenticate (String login, String password, ServerSession session) {
		if(authMap.containsKey(login)) {
            return authMap.get(login).equals(password);
		}
		return false;
	}

}
