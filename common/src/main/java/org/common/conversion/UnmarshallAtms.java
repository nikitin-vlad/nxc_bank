package org.common.conversion;

import java.io.File;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.common.accounts.Accounts;
import org.common.atms.Atms;
import org.common.configs.Config;

public class UnmarshallAtms extends XMLConversion {
	
	
	public static Atms unMarshall(){
		try {
		 
			URL resoursePath = Accounts.class.getResource(Config.atmsFile);
		    String path = resoursePath.getPath();
		    
			File file = new File(path);
			JAXBContext jaxbContext = JAXBContext.newInstance(Atms.class);
		
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			Atms atms = (Atms) jaxbUnmarshaller.unmarshal(file);
			
			return atms;
			
		} catch (JAXBException e) {
		  	e.printStackTrace();
		}
		
		return null;
	}
	
}
