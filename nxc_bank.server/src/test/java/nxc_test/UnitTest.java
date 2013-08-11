package nxc_test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import no.nxc.bank.server.SSLConfiguration;

import org.junit.Test;

public class UnitTest {

	@Test
	public void test() {
		assertTrue(true);
		System.out.println("test finished");
	}
	
	@Test
	public void sslConfigTest() throws FileNotFoundException, JAXBException {
		SSLConfiguration sc = new SSLConfiguration();
		sc.setKeypass("serverkeypass");
		sc.setStorepass("serverstorepass");
		sc.setJkskeyPath("src/main/resources/ssl/server.jks");
		sc.setPort(8888);
		
		File conf = new File("src/main/resources/jaxb/ssl_config.xml");
		
		if (!conf.exists()) {
			JAXBContext jaxbContext = JAXBContext.newInstance(SSLConfiguration.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.marshal(sc, conf);
			jaxbMarshaller.marshal(sc, System.out);
		}
	}

}
