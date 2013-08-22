package org.common.conversion;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class XMLConversion {

	
	public static void marshall(Object object, String path){
		  try {

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

    @SuppressWarnings("unchecked")
	public static < E > E unMarshall(Class< E > typeClass, String path){
        try {

            File file = new File(path);
            JAXBContext jaxbContext = JAXBContext.newInstance(typeClass);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (E)jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }


//	public static void main(String[] args) throws Exception {
//		Account acc = new Account();
//		acc.setAmount(100.00);
//		acc.setCardNumber("1000000000000");
//		acc.setPassword("pass");
//		acc.setStatus(true);
//		marshall(acc, "D:/JavaDev/nxc_bank/workspace/nxc_bank/common/src/main/java/org/common/accounts/accounts.xml");
//	}
//
//	public static void main(String[] args) throws Exception {
//        Atms atms = unMarshall(Atms.class, "target/classes" + Config.atmsFile);
//        System.out.println(atms.getAtm(5).getBills());
//    }
//	public static void main(String[] args) throws Exception {
//        Atms atms = new Atms();
//        marshall(atms, "target/classes" + Config.atmsFile);
//    }
}