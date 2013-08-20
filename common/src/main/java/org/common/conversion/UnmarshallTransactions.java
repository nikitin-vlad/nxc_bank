package org.common.conversion;

import java.io.File;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.common.accounts.Accounts;
import org.common.configs.Config;
import org.common.transactions.Transactions;

public class UnmarshallTransactions extends XMLConversion {
	
	public static Transactions unMarshall(){
		try {
		 
			URL resoursePath = Accounts.class.getResource(Config.transactionsFile);
		    String path = resoursePath.getPath();
		    
			File file = new File(path);
			JAXBContext jaxbContext = JAXBContext.newInstance(Transactions.class);
		
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Transactions transactions = (Transactions) jaxbUnmarshaller.unmarshal(file);
			
			return transactions;
			
		} catch (JAXBException e) {
		  	e.printStackTrace();
		}
		
		return null;
	}
	
}
