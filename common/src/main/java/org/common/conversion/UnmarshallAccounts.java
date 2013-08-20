package org.common.conversion;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.common.accounts.Accounts;

public class UnmarshallAccounts extends XMLConversion {
	
	private static String path = "filepath";
	
	public static Accounts unMarshall(){
		try {
		 
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
