package com.example.model;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class CandlekindsTest {
    @Test
    public void jaxbUnmarshallingTest() throws Exception {
//        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(Candlekinds.class);

        JAXBContext jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
                .createContext(new Class[] {Candlekinds.class}, null);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            File file = new File("/home/greyog/tmp/xml_0/0candles.xml");

            Candlekinds candlekinds = (Candlekinds) unmarshaller.unmarshal(file);

            System.out.println(candlekinds.toString());
            assert candlekinds.kindList.size() > 0;
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
    }
}