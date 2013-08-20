package org.common.conversion;

import java.io.File;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XMLConversion {

	
	public static void marshall(Object object, String confPath){
		  try {
			    URL resoursePath = object.getClass().getResource(confPath);
			    String path = resoursePath.getPath();
			    
				File file = new File(path);
				JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		 
				jaxbMarshaller.marshal(object, file);
				jaxbMarshaller.marshal(object, System.out);
		 
	      } catch (JAXBException e) {
				e.printStackTrace();
	      }
	}
}