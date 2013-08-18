package org.server.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.cert.Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.common.operations.OperationResponse;
import org.common.operations.OperationResponseStatus;
import org.common.operations.OperationRequest;

public class ClientHandler extends Thread {
    
    SSLSocket clientSocket;
    boolean run = true;

    ClientHandler(SSLSocket s) {
        clientSocket = s;
    }
    
    protected int getClientId () throws Exception {
        SSLSession session = clientSocket.getSession();
        Certificate[] certificates = session.getPeerCertificates();
        if (certificates.length != 1) {
        	throw new Exception ("Wrong client certificate");
        }
        return Arrays.hashCode(certificates[0].getPublicKey().getEncoded());
    }
    
    public void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            int atmId = this.getClientId();
        	in = new ObjectInputStream(clientSocket.getInputStream());
        	out = new ObjectOutputStream(clientSocket.getOutputStream());
        	OperationRequest request = null;
        	OperationResponse response = null;
            while (run) {
                request = (OperationRequest)in.readObject();
                System.out.println("Client Says :" + request);
                
                response = new OperationResponse(OperationResponseStatus.OK, "");
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