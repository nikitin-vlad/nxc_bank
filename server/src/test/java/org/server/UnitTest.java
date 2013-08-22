package org.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.common.accounts.Account;
import org.common.atms.Atm;
import org.common.configs.SSLConfiguration;
import org.junit.Test;

public class UnitTest {

	@Test
	public void sslConfigTest() throws FileNotFoundException, JAXBException {
		SSLConfiguration sc = new SSLConfiguration();
		sc.setKeypass("serverkeypass");
		sc.setStorepass("serverstorepass");
		sc.setJkskeyPath("src/main/resources/ssl/server.jks");
		sc.setPort(8888);
		
		File conf = new File("src/main/resources/jaxb/ssl_config.xml");
		
		if (!conf.exists()) {
			JAXBContext jaxbContext = JAXBContext.newInstance(SSLConfiguration.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.marshal(sc, conf);
			jaxbMarshaller.marshal(sc, System.out);
		}
	}

	@Test
	public void getCashTest() throws Exception {
		Atm atm = new Atm();
		HashMap<Integer, Integer> bills = atm.getBills();
		bills.put(1, 10000);
		bills.put(2, 10000);
		bills.put(5, 10000);
		bills.put(10, 10000);
		bills.put(20, 10000);
		bills.put(50, 10000);
		bills.put(100, 10000);
		bills.put(200, 10000);
		bills.put(500, 10000);
		
		Account account = new Account();
		account.setAmount(5560.00);
		
		System.out.println(atm.getCash(account, 2600));
	}
}
