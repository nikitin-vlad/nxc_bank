package org.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.common.atms.Atm;
import org.common.configs.SSLConfiguration;
import org.common.operations.OperationResponse;
import org.common.operations.OperationResponseStatus;

public class SecureSocketServer {
	private KeyStore keyStore;
	
	public SecureSocketServer(ClientHandlerFactory clientFactory, SSLConfiguration sslConf) {
        try {
        	this.initKeyStore(this.getClass().getClassLoader().getResource(sslConf.getJkskeyPath()).getPath(), sslConf.getStorepass());
        	SSLServerSocket serverSocket = this.createServerSocket(sslConf.getKeypass(), sslConf.getPort());
        	
            while (true) {
                // Accept incoming connections.
            	System.out.println("Server waiting for connection");
            	SSLSocket sslSocket = (SSLSocket) serverSocket.accept();
                
                String atmName = this.getAtmName(sslSocket);
                Atm atm = Server.getAtms().getAtm(atmName);
                if (atm == null) {
                	ObjectOutputStream out = null;
                	try {
                    	out = new ObjectOutputStream(sslSocket.getOutputStream());
                    	out.writeObject(new OperationResponse(OperationResponseStatus.NonRegisteredAtm, "this atm is'nt registered, contact administrator"));
                    	sslSocket.close();
                	} catch (IOException e) {
                	} finally {
                		try {
                			out.close();
                		} catch (IOException e) {}              			
                	}
                	continue;
                }
                
                ClientHandler cliThread = clientFactory.create(sslSocket, atm);
                cliThread.start();
            }

        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	protected void initKeyStore (String keystoreLocation, String storepass) throws Exception {
        System.setProperty("javax.net.ssl.trustStore", keystoreLocation);
        System.setProperty("javax.net.ssl.trustStorePassword", storepass);
        
        keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(keystoreLocation), storepass.toCharArray());
	}
	
	protected SSLServerSocket createServerSocket (String keypass, int port) throws Exception {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory
                .getInstance("SunX509");
        keyManagerFactory.init(keyStore, keypass.toCharArray());
        
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(keyManagerFactory.getKeyManagers(), null, null);
        
        SSLServerSocketFactory sslServerSocketFactory = sc
                .getServerSocketFactory();
        
        SSLServerSocket serverSocket = (SSLServerSocket) sslServerSocketFactory
                .createServerSocket(port);
        serverSocket.setNeedClientAuth(true);
        
        return serverSocket;
	}
	
	protected String getAtmName (SSLSocket clientSocket) {
    	try {
	        SSLSession session = clientSocket.getSession();
	        Certificate[] certificates = session.getPeerCertificates();
	        if (certificates.length != 1) {
	        	return null;
	        }
			return keyStore.getCertificateAlias(certificates[0]);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
	
}
