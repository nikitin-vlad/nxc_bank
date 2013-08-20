package org.server;

import java.awt.EventQueue;
import java.security.KeyStore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.server.panels.MainForm;
import org.common.accounts.Accounts;
import org.common.atms.Atms;
import org.common.configs.Config;

 

public class Server {

	private static Accounts accounts = new Accounts();
	private static Atms atms = new Atms();
	public static KeyStore keyStore;
    
	public static boolean isRunning = false;
	private static MainForm window;
	private static DetailsUpdater dataUpdater;
	private static MainForm gui;
	
	public static void main(String[] args) throws Exception {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(SSLConfiguration.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		final SSLConfiguration serverConf = (SSLConfiguration) jaxbUnmarshaller.unmarshal(Server.class.getResourceAsStream(Config.sslConfig));
		
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
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			        isRunning = true;
					Server.initGui();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}
	
	protected static void initGui() {
		gui = new MainForm();
		gui.frmBankServer.setVisible(true);
	}

	public static Accounts getAccounts() {
		return accounts;
	}
	
	public static Atms getAtms() {
		return atms;
	}

	public static void startUpdater(){
		if (dataUpdater == null) {
			dataUpdater = new DetailsUpdater();
		}
	}
	
	public static void updateData() {
		window.loadAccounts(true);
		window.loadAtms(true);
	}

	public static void updateDetailPanelsData() {
		gui.updateAccountDetailsPanel();
		gui.updateAtmDetailsPanel();
	}
}
