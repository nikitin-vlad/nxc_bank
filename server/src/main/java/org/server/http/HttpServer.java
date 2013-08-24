package org.server.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.common.accounts.Account;
import org.common.accounts.Accounts;
import org.common.operations.OperationRequest;
import org.common.operations.OperationResponse;
import org.common.operations.OperationType;
import org.server.Controller;
import org.server.Server;

public class HttpServer {
	private static Accounts accounts = Server.getAccounts();
	
    public HttpServer(int port) {
    	try {
	        ServerSocket ss = new ServerSocket(port);
	        while (true) {
	            Socket s = ss.accept();
	            new Thread(new SocketProcessor(s)).start();
	        }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    private static class SocketProcessor implements Runnable {

        private Socket s;
        private InputStream is;
        private OutputStream os;
        private String command;
        private HashMap<String, String> headers;
        BufferedReader br;
        

        private SocketProcessor(Socket s) throws Exception {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
            this.br = new BufferedReader(new InputStreamReader(is));
            setCommand(getUri());
            headers = readInputHeaders();
            System.out.println("helllo");
        }

        public void run() {
            try {
            	String auth = headers.get("Authorization");
            	if (!allowUser(auth)) { 
            		String response = "HTTP/1.1 401 Not Authorized\r\n" + 
            					"WWW-Authenticate: Basic realm=\"insert realm\"\r\n" + 
            					"Connection: close\r\n\r\n";
            		os.write(response.getBytes());
                    os.flush();
            	} else {
            		Controller controller = new Controller();
            		OperationRequest request;
            		OperationResponse res = null;
            		String[] logPass = getUserPassDecoded(auth).split(":");
            		String htmlRoot = this.getClass().getClassLoader().getResource("root").getPath();
            		
            		FileInputStream fis;
            		File file;
            		String responseHeaders;
            		byte[] buffer;
            		switch (command)
            		{
            		case "/operations/balance":
            			request = new OperationRequest(logPass[0], Integer.parseInt(logPass[1]), OperationType.Balance);
            			res = controller.handleRequest(request);
            			String json = "{\"amount\":" + res.getMessage() + "}";
            			writeResponse(json.getBytes());
            			break;
            		case "/operations/transactions":
            			request = new OperationRequest(logPass[0], Integer.parseInt(logPass[1]), OperationType.Transactions);
            			res = controller.handleRequest(request);
            			writeResponse(res.getMessage().getBytes());
            			break;
            		case "/":
            			file = new File(htmlRoot + "/index.html");
            			fis = new FileInputStream(file);
            			buffer = new byte[(int) file.length()];
            			fis.read(buffer);
            			writeResponse(buffer);
            			break;
            		default:
            			file = new File(htmlRoot + command);
            			if (file.exists()) {
	            			fis = new FileInputStream(file);
	            			buffer = new byte[(int) file.length()];
	            			fis.read(buffer);
	            			writeResponse(buffer);
            			} else {
            				responseHeaders = "HTTP/1.1 404 NOT FOUND\r\n" +
	            					"Content-Type: text/html\r\n" +
	            					"Connection: close\r\n\r\n";
	            			os.write(responseHeaders.getBytes());
	            			os.flush();
            			}
            		}
            	}
            } catch (Throwable t) {
                /*do nothing*/
            } finally {
                try {
                    s.close();
                } catch (Throwable t) {
                    /*do nothing*/
                }
            }
        }

        private void writeResponse(byte[] b) throws Exception {
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Connection: close\r\n\r\n";
            os.write(response.getBytes());
            os.write(b);
            os.flush();
        }

        private HashMap<String, String> readInputHeaders() throws Exception {
        	HashMap<String, String> result = new HashMap<String, String>();
            while(true) {
            	String s = br.readLine();
                if(s == null || s.trim().length() == 0) {
                    break;
                }
                String[] kv = s.split(":");
                result.put(kv[0].trim(), kv[1].trim());
            }
            return result;
        }
        
        private String getUri() throws Exception {
        	
        	String s = br.readLine();
        	if(s == null || s.trim().length() == 0)
        		return null;
    		return s.split(" ")[1];
        }
        
        protected boolean allowUser(String auth) throws Exception {  
        	String userpassDecoded = getUserPassDecoded(auth);
        	
        	if (userpassDecoded == null) {
        		return false;
        	}
          
        	String[] logPass = userpassDecoded.split(":");
        	Account acc = accounts.getAccount(logPass[0], Integer.parseInt(logPass[1]));
            // Check our user list to see if that user and password are "allowed"  
            if (acc != null && acc.isStatus()) {  
                return true;  
            } else {  
                return false;  
            }  
        }
        
        protected String getUserPassDecoded (String auth) throws Exception {
            if (auth == null) {  
                return null;  // no auth  
            }  
            if (!auth.toUpperCase().startsWith("BASIC ")) {   
                return null;  // we only do BASIC  
            }  
            // Get encoded user and password, comes after "BASIC "  
            String userpassEncoded = auth.substring(6);  
            // Decode it, using any base 64 decoder  
            sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();  
            return new String(dec.decodeBuffer(userpassEncoded));  
        }

		public String getCommand() {
			return command;
		}

		public void setCommand(String command) {
			this.command = command;
		}
    }
}