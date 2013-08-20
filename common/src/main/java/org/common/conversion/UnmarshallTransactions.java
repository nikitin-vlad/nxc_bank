package org.common.conversion;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.common.transactions.Transactions;

public class UnmarshallTransactions extends XMLConversion {
	
	private static String path = "filepath";
	
	public static Transactions unMarshall(){
		try {
		 
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
