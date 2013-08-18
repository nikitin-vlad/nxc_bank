package org.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SecureSocketServer {
    public SecureSocketServer(ClientHandlerFactory clientFactory, SSLConfiguration sslConf) {
        String storepass = sslConf.getStorepass();
        String keypass = sslConf.getKeypass();
        String keystoreLocation = sslConf.getJkskeyPath();

        System.setProperty("javax.net.ssl.trustStore", keystoreLocation);
        System.setProperty("javax.net.ssl.trustStorePassword", storepass);

        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");

            keyStore.load(new FileInputStream(keystoreLocation), storepass.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory
                    .getInstance("SunX509");
            keyManagerFactory.init(keyStore, keypass.toCharArray());

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(keyManagerFactory.getKeyManagers(), null, null);
            SSLServerSocketFactory sslServerSocketFactory = sc
                    .getServerSocketFactory();
            SSLServerSocket serverSocket = (SSLServerSocket) sslServerSocketFactory
                    .createServerSocket(sslConf.getPort());
            serverSocket.setNeedClientAuth(true);
            
            while (true) {
                try {
                    // Accept incoming connections.
                    SSLSocket sslSocket = (SSLSocket) serverSocket.accept();

                    ClientHandler cliThread = clientFactory.create(sslSocket);
                    cliThread.start();
                } catch (IOException ioe) {
                    System.out
                            .println("Exception encountered on accept. Ignoring. Stack Trace :");
                    ioe.printStackTrace();
                }
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        	e.printStackTrace();
        }
    }
}
