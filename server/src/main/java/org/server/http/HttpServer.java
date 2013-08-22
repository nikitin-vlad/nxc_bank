package org.server.http;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.common.accounts.Account;
import org.common.atms.Atm;
import org.server.Server;

public class HttpServer {
	private static Hashtable<String, String> validUsers = new Hashtable<String, String>();
	static {
    	for (Account ac : Server.getAccounts().getAll() ) {
    		validUsers.put(ac.getCardNumber() + ":" + String.valueOf(ac.getPassword()), "authorized");
    	}		
	}
	
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
            command = getUri();
            headers = readInputHeaders();
            System.out.println("helllo");
        }

        public void run() {
            try {
            	String auth = headers.get("Authorization");
            	if (!allowUser(auth)) { 
            		String response = "HTTP 401 Not Authorized\r\n" + 
            					"WWW-Authenticate: Basic realm=\"insert realm\"\r\n" + 
            					"Connection: close\r\n\r\n";
            		os.write(response.getBytes());
                    os.flush();
            	} else {
            		writeResponse("<html><body><h1>Hello from Habrahabr</h1></body></html>");
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

        private void writeResponse(String s) throws Exception {
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + s.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            String result = response + s;
            os.write(result.getBytes());
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
          
            // Check our user list to see if that user and password are "allowed"  
            if ("authorized".equals(validUsers.get(userpassDecoded))) {  
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
    }
}