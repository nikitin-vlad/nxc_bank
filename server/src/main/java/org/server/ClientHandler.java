package org.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.common.operations.OperationResponse;
import org.common.operations.OperationResponseStatus;
import org.common.operations.OperationRequest;

public class ClientHandler extends Thread {
    
    SSLSocket clientSocket;
    String name = "";
    boolean run = true;

    ClientHandler(SSLSocket s) {
        clientSocket = s;
        try {
        	name = this.getClientName();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
    }
    
    protected String getClientName() {
    	try {
	        SSLSession session = clientSocket.getSession();
	        Certificate[] certificates = session.getPeerCertificates();
	        if (certificates.length != 1) {
	        	return null;
	        }
			return Server.keyStore.getCertificateAlias(certificates[0]);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    public void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            System.out.println(this.getClientName());
        	out = new ObjectOutputStream(clientSocket.getOutputStream());
        	in = new ObjectInputStream(clientSocket.getInputStream());
        	OperationRequest request = null;
        	OperationResponse response = null;
            while (run) {
                request = (OperationRequest)in.readObject();
                
                Controller controller = new Controller(this);
                response = controller.handleRequest(request);
                
                out.writeObject(response);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
                System.out.println("...Stopped");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}