package org.common.conversion;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.common.atms.Atms;

public class UnmarshallAtms extends XMLConversion {
	
	private static String path = "filepath";
	
	public static Atms unMarshall(){
		try {
		 
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
