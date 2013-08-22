package org.common.atms;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.common.configs.Config;
import org.common.conversion.XMLConversion;
import org.hamcrest.Matchers;

import static ch.lambdaj.Lambda.*;


@XmlRootElement
public class Atms {
	@XmlElement(name = "atm")
	private ArrayList<Atm> data = new ArrayList<Atm>();
	private boolean blocked = false;
	//private static String path = "common/target/resources" + Config.atmsFile;
	private static String path = "target/classes" + Config.atmsFile;
//	private Atms() {
//		for (int i = 0, l = 5; i < l; i++) {
//			Atm atm = new Atm();
//			atm.setId("ATM-"+(i+1));
//			atm.setStatus(true);
//			HashMap<Integer, Integer> bills = new HashMap<Integer, Integer>();
//			bills.put(12 + 5*i, 10 + 2*i);
//			bills.put(12 + 2*i, 10 + 6*i);
//			bills.put(12 + 9*i, 10 + 7*i);
//			atm.setBills(bills);
//			data.add(atm);
//			XMLConversion.marshall(this, path);
//		}
//      Atms atms = UnmarshallAtms.unMarshall(path);
//		Atms atms = XMLConversion.unMarshall(Atms, path);
//		data.addAll((Collection<? extends Atm>) atms);
//	}
	
	public ArrayList<Atm> init(){
		Atms atms = XMLConversion.unMarshall(Atms.class, path);
		int i = 1;
		try{
			while(atms.getAtm(i) != null){
				data.add(atms.getAtm(i));
				i++;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			e.getMessage();			
		}

		return data;
	}
	
//	public static void main(String[] args){
//		Atms atms = new Atms();
//		Atm atm = new Atm();
//		atm.setId("ATM-" + 1530);
//		atm.setStatus(true);
//		HashMap<Integer, Integer> bills = new HashMap<Integer, Integer>();
//		bills.put(12 + 50, 10 + 20);
//		bills.put(12 + 20, 10 + 60);
//		bills.put(12 + 90, 10 + 70);
//		atm.setBills(bills);
//		atms.addAtm(atm);
//		atms.removeAtm("ATM-1530");
//		List<Atm> atsm = atms.init();
//		System.out.println(atsm.toString());
//	}
	
	public void clear() {
		data.clear();
	}
	
	public Atm getAtm(String id) {
		List<Atm> accounts = filter(having(on(Atm.class).getId(), Matchers.equalTo(id)), data);
		Object[] values = accounts.toArray();
		return (values.length > 0) ? (Atm) values[0] : null;
	}
	
	public void addAtm(Atm atm) {
		this.init();
		data.add(atm);
		XMLConversion.marshall(this, path);
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
		this.init();
		List<Atm> atmList = filter(having(on(Atm.class).getId(), Matchers.equalTo(id)), data);
		if (!atmList.isEmpty()) {
			Atm atm = atmList.get(0);
			data.remove(atm);
		}
		XMLConversion.marshall(this, path);
	}

	public boolean isExisting(String id) {
		List<Atm> atmList = filter(having(on(Atm.class).getId(), Matchers.equalTo(id)), data);
		if (atmList.isEmpty()){
			return false;
		}
		return true;
	}
}
