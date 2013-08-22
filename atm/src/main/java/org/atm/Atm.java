package org.atm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.common.configs.Config;
import org.common.configs.SSLConfiguration;
import org.common.conversion.XMLConversion;

public class Atm {

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		
		exec.execute(new Runnable() {

			@Override
			public void run() {
				new SecureSocketClient(XMLConversion.unMarshall(SSLConfiguration.class, Config.sslConfig));
			}
		});
		
		System.out.println("Connection thread was started");
	}

}
