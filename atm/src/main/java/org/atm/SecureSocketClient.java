package org.atm;

import java.io.BufferedReader;
import java.io.FileInputStream;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.common.configs.SSLConfiguration;
import org.common.operations.OperationRequest;
import org.common.operations.OperationResponse;
import org.common.operations.OperationType;

public class SecureSocketClient {
	
	private SSLSocket socket;
	private KeyStore keyStore;
	
    public SecureSocketClient(SSLConfiguration config) {
        try {
        	this.initKeyStore(this.getClass().getClassLoader().getResource(config.getJkskeyPath()).getPath(), config.getStorepass());
        	this.initSocket(config.getKeypass(), config.getPort());
        	this.handleSocket();
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
	
    protected void initSocket (String keypass, int port) throws Exception {
    	KeyManagerFactory keyManagerFactory = KeyManagerFactory
                .getInstance("SunX509");
        keyManagerFactory.init(keyStore, keypass.toCharArray());
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        SSLSocket cliSocket = (SSLSocket) sslSocketFactory.createSocket(
                "localhost", port);
        cliSocket.startHandshake();
        this.socket = cliSocket;
	}
    
    public void handleSocket () {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        ObjectInputStream socketReader = null;
        ObjectOutputStream socketWriter = null;
        try {
	        if (!socket.isConnected()) {
	        	socket.startHandshake();
	        }
	        socketReader = new ObjectInputStream(socket.getInputStream());
        	socketWriter = new ObjectOutputStream(socket.getOutputStream());
	        while (true) {
	        	OperationRequest request = this.getRequestObject(in);
	        	socketWriter.writeObject(request);
	        	socketWriter.flush();
	        	
	        	OperationResponse response = (OperationResponse)socketReader.readObject();
	        	System.out.println(response.getStatus() + " "  + response.getMessage());
	        }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	try {
		        in.close();
        		socketWriter.close();
		        socketReader.close();
		        socket.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        }

    }
    
    public OperationRequest getRequestObject (BufferedReader reader) throws IOException {
    	OperationRequest request = new OperationRequest();
    	
    	System.out.println("enter card number");
    	String cardNumber;
		cardNumber = reader.readLine();
    	request.setCardNumber(cardNumber);
    	
    	System.out.println("enter your password");
    	int pass = Integer.parseInt(reader.readLine());
    	request.setPass(pass);
    	
    	System.out.println("enter operation type");
    	OperationType[] values = OperationType.values();
    	OperationType operationType = null;
    	int id = 0;
    	while (true) {
	    	for (int key = 0; key < values.length; key++) {
	        	System.out.println(key + ": " + values[key]);
	    	}
	    	id = Integer.parseInt(reader.readLine());
	    	if (id < values.length) {
		    	operationType = values[id];
		    	break;
	    	} else {
	    		System.out.println("Wrong operatoin id, try again");
	    	}
    	}
    	if (operationType == OperationType.GetCash ||
    			operationType == OperationType.ReloadAmount) {
    		System.out.println("Enter ammount");
    		String ammount = reader.readLine();
    		request.setData(ammount);
    	}
    	System.out.println("sending data");
    	request.setOperation(operationType);
    	
		return request;
    }

}
