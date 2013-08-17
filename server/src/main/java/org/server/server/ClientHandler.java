package org.server.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.cert.Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

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
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            int atmId = this.getClientId();
        	in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
        	out = new PrintWriter(new OutputStreamWriter(
                    clientSocket.getOutputStream()));

            while (run) {
                String clientCommand = in.readLine();
                System.out.println("Client Says :" + clientCommand);

                if (clientCommand.equalsIgnoreCase("quit")) {
                    run = false;
                } else {
                    // echo back
                    out.println(clientCommand);
                    out.flush();
                }
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