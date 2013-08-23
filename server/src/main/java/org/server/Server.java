package org.server;

import java.awt.EventQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.server.http.HttpServer;
import org.server.panels.MainForm;
import org.common.accounts.Accounts;
import org.common.atms.Atms;
import org.common.configs.Config;
import org.common.configs.SSLConfiguration;
import org.common.conversion.XMLConversion;

 

public class Server {

	private static Accounts accounts = new Accounts();
	private static Atms atms = new Atms();
    
	public static boolean isRunning = false;
	private static DetailsUpdater dataUpdater;
	private static MainForm gui;
	
	public static void main(String[] args) throws Exception {
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		accounts = accounts.getAccounts();
		atms = atms.getAtms();
		
		exec.execute(new Runnable() {
			@Override
			public void run() {
				new SecureSocketServer(new ClientHandlerFactory(), XMLConversion.unMarshall(SSLConfiguration.class, Config.sslConfig));
			}
		});
		
		exec.execute(new Runnable() {
			@Override
			public void run() {
				new HttpServer(8080);
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
		gui.loadAccounts(true);
		gui.loadAtms(true);
	}

	public static void updateDetailPanelsData() {
		gui.updateAccountDetailsPanel();
		gui.updateAtmDetailsPanel();
	}

	public static void pushDataToStorage() {
		if (accounts != null) accounts.store();
		if (atms != null) atms.store();
	}
}
