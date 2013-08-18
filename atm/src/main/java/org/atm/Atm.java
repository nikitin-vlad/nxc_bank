package org.atm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Atm {

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		
		exec.execute(new Runnable() {

			@Override
			public void run() {
				new SecureSocketClient();
			}
		});
		
		System.out.println("Connection thread was started");
	}

}
