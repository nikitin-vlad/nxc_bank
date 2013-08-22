package org.common.atms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.common.conversion.UnmarshallAtms;
import org.hamcrest.Matchers;

import static ch.lambdaj.Lambda.*;



public class Atms {
//	@XmlElement(name = "AtmList")
	private ArrayList<Atm> data = new ArrayList<Atm>();
	private boolean blocked = false;
	
	public Atms() {
//		for (int i = 0, l = 5; i < l; i++) {
//			Atm atm = new Atm();
//			atm.setId("ATM-"+(i+1));
//			atm.setStatus(true);
//			data.add(atm);
//		}	
		Atms atms = UnmarshallAtms.unMarshall();
		data.addAll((Collection<? extends Atm>) atms);
	}
	
	public void clear() {
		data.clear();
	}	
	
	public Atm getAtm(String id) {
		List<Atm> accounts = filter(having(on(Atm.class).getId(), Matchers.equalTo(id)), data);
		Object[] values = accounts.toArray();
		return (values.length > 0) ? (Atm) values[0] : null;
	}
	
	public void addAtm(Atm atm) {
		data.add(atm);
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public int count() {
		return data.size();
	}

	public Atm getAtm(int index) {
		Object[] values = data.toArray();
		if (values.length > 0 && ( (index-1) <= values.length && (index-1) >= 0) ) {
			return (Atm) values[index-1];
		}
		return null;
	}

	public void removeAtm(String id) {
		List<Atm> atmList = filter(having(on(Atm.class).getId(), Matchers.equalTo(id)), data);
		if (!atmList.isEmpty()) {
			Atm atm = atmList.get(0);
			data.remove(atm);
		}
	}

	public boolean isExisting(String id) {
		List<Atm> atmList = filter(having(on(Atm.class).getId(), Matchers.equalTo(id)), data);
		if (atmList.isEmpty()){
			return false;
		}
		return true;
	}	
}
