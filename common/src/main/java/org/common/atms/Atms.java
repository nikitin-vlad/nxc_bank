package org.common.atms;

import java.util.HashMap;

public class Atms {
	private HashMap<String, Atm> data = new HashMap<String, Atm>();
	
	public Atm getAtm(String id) {
		return null;
	}
	
	public void addAccount(Atm atm) {
		data.put(atm.getId(), atm);
	}	
}
