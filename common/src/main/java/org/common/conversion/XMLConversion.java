package org.common.conversion;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.common.accounts.Account;
import org.common.accounts.Accounts;
import org.common.atms.Atm;
import org.common.atms.Atms;
import org.common.configs.Config;

public class XMLConversion {

	
	public static void marshall(Object object, String confPath){
		  try {
			    //URL resoursePath = object.getClass().getResource(confPath);
			    //String path = resoursePath.getPath();
			    
				File file = new File(confPath);
				JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		 
				jaxbMarshaller.marshal(object, file);
				jaxbMarshaller.marshal(object, System.out);
		 
	      } catch (JAXBException e) {
				e.printStackTrace();
	      }
	}
//	public static void main(String[] args) throws Exception {
//		Account acc = new Account();
//		acc.setAmount(100.00);
//		acc.setCardNumber("1000000000000");
//		acc.setPassword("pass");
//		acc.setStatus(true);
//		marshall(acc, "D:/JavaDev/nxc_bank/workspace/nxc_bank/common/src/main/java/org/common/accounts/accounts.xml");
//	}
	
//	public static void main(String[] args) throws Exception {
//		Atms atms = new Atms();
//		marshall(atms, "D:/JavaDev/nxc_bank/workspace/nxc_bank/common/src/main/java/org/common/atms/atms.xml");
//		
//	}	
}