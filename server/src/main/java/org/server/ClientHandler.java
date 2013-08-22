package org.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.net.ssl.SSLSocket;

import org.common.operations.OperationResponse;
import org.common.operations.OperationRequest;

public class ClientHandler extends Thread {
    
    private SSLSocket clientSocket;
    private String atmName;

    ClientHandler(SSLSocket s, String atmN) {
        clientSocket = s;
        atmName = atmN;
    }
    
    @Override
    public void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
        	out = new ObjectOutputStream(clientSocket.getOutputStream());
        	in = new ObjectInputStream(clientSocket.getInputStream());
        	OperationRequest request = null;
        	OperationResponse response = null;
        	Controller controller = new Controller();
            while (true) {
                request = (OperationRequest)in.readObject();
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