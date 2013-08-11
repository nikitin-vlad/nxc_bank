package no.nxc.bank.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;

public class ClientHandler extends Thread {
    
    SSLSocket clientSocket;
    boolean run = true;

    ClientHandler(SSLSocket s) {
        clientSocket = s;
    }

    public void run() {
        BufferedReader in = null;
        PrintWriter out = null;

        try {
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