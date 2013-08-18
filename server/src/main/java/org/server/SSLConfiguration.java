package org.server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SSLConfiguration {
	String storepass;
	String keypass;
	String jkskeyPath;
	int port;
	
	public int getPort() {
		return port;
	}
	
	@XmlElement
	public void setPort(int port) {
		this.port = port;
	}

	public String getStorepass() {
		return storepass;
	}
	
	public String getKeypass() {
		return keypass;
	}
	
	public String getJkskeyPath() {
		return jkskeyPath;
	}
	@XmlElement
	public void setStorepass(String storepass) {
		this.storepass = storepass;
	}
	@XmlElement
	public void setKeypass(String keypass) {
		this.keypass = keypass;
	}
	@XmlElement
	public void setJkskeyPath(String jkskeyPath) {
		this.jkskeyPath = jkskeyPath;
	}
}
