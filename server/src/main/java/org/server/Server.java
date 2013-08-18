package org.server;

import java.awt.EventQueue;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.server.panels.MainForm;


 

public class Server {

	public static void main(String[] args) throws Exception {
		System.out.println("Server initialization goes here");
		
		File file = new File("src/main/resources/jaxb/ssl_config.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(SSLConfiguration.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		final SSLConfiguration serverConf = (SSLConfiguration) jaxbUnmarshaller.unmarshal(file);
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		exec.execute(new Runnable() {
			SSLConfiguration sc;
			{
				this.sc = serverConf;
			}
			
			@Override
			public void run() {
				new SecureSocketServer(new ClientHandlerFactory(), this.sc);
			}
		});
		System.out.println("Server thread was started");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmBankServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}
}
