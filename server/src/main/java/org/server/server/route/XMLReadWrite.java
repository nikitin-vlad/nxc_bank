package org.server.server.route;

import org.server.server.entity.XMLEntity;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

class XMLReadWrite {
    private XMLEntity entity;
    private String filePath = "users/";
    private String XMLfile = "users.xml";
    private File file;


    public XMLReadWrite (XMLEntity entity){
        this.entity = entity;
        this.file = new File( filePath + XMLfile );
    }

    public XMLEntity getData(){
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(entity.getClass());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            entity = (XMLEntity) jaxbUnmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public void setData(){
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(entity.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(entity, file);
            jaxbMarshaller.marshal(entity, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


}
