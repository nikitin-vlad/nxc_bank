package no.nxc.bank.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class SecureSocketServer {
    public static void main(String[] args) {
        String storepass = "serverstorepass";
        String keypass = "serverkeypass";

        String keystoreLocation = SecureSocketServer.class.getClassLoader()
        		.getResource("ssl/server.jks")
        		.getFile();

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
            SSLServerSocket s = (SSLServerSocket) sslServerSocketFactory
                    .createServerSocket(8888);
            s.setNeedClientAuth(true);
            
            while (true) {
                try {
                    // Accept incoming connections.
                    SSLSocket sslSocket = (SSLSocket) s.accept();

                    ClientHandler cliThread = new ClientHandler(sslSocket);
                    cliThread.start();
                } catch (IOException ioe) {
                    System.out
                            .println("Exception encountered on accept. Ignoring. Stack Trace :");
                    ioe.printStackTrace();
                }
            }

        } catch (Exception e) {
        	e.printStackTrace();
            System.err.println(e.toString());
        }
    }
}
