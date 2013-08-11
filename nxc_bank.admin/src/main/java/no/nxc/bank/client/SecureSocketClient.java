package no.nxc.bank.client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SecureSocketClient {
    public static void main(String[] args) {

        String storepass = "clientstorepass";
        String keypass = "clientkeypass";

        String keystoreLocation = SecureSocketClient.class.getClassLoader()
        		.getResource("ssl/client.jks")
        		.getFile();
        
        char[] keyStorePassword = storepass.toCharArray();
        System.setProperty("javax.net.ssl.trustStore", keystoreLocation);
        System.setProperty("javax.net.ssl.trustStorePassword", storepass);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(keystoreLocation), keyStorePassword);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory
                    .getInstance("SunX509");
            keyManagerFactory.init(keyStore, keypass.toCharArray());
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            SSLSocket c = (SSLSocket) sslSocketFactory.createSocket(
                    "localhost", 8888);
            c.startHandshake();

            BufferedReader r = null;
            PrintWriter w = null;

            try {
                r = new BufferedReader(
                        new InputStreamReader(c.getInputStream()));
                w = new PrintWriter(new OutputStreamWriter(c.getOutputStream()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            String m = null;
            while ((m = in.readLine()) != null) {

                w.println(m);
                w.flush();

                if (!"QUIT".equalsIgnoreCase(m)) {
                    System.out.println("Server Says : " + r.readLine());
                } else {
                    break;
                }

            }
            w.close();
            r.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

}
