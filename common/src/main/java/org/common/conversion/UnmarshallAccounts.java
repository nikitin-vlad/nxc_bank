package org.common.conversion;

import java.io.File;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.common.accounts.Accounts;
import org.common.configs.Config;

public class UnmarshallAccounts extends XMLConversion {
	

	
	public static Accounts unMarshall(){
		try {

			URL resoursePath = Accounts.class.getResource(Config.accountsFile);
		    String path = resoursePath.getPath();
		    
			File file = new File(path);
			JAXBContext jaxbContext = JAXBContext.newInstance(Accounts.class);
		
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Accounts accounts = (Accounts) jaxbUnmarshaller.unmarshal(file);
			
			return accounts;
			
		} catch (JAXBException e) {
		  	e.printStackTrace();
		}
		
		return null;
	}
	
}
