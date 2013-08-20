package org.common.conversion;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XMLConversion {

	public static void marshall(Object object){
		  try {
				File file = new File("filepath");
				JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		 
				jaxbMarshaller.marshal(object, file);
				jaxbMarshaller.marshal(object, System.out);
		 
	      } catch (JAXBException e) {
				e.printStackTrace();
	      }
	}
	
	public static Object unMarshall(){
		return null;
	}

}