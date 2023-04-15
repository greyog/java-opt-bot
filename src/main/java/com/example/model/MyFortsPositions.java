package com.example.model;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.persistence.jaxb.JAXBContextFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement
public class MyFortsPositions {
    @XmlElement
    private List<MyFortsPosition> positions;
    @XmlElement
    private String timeStamp;

    public MyFortsPositions() {
        this.positions = new ArrayList<>();
    }

    public MyFortsPositions(List<FortsPosition> fortsOptionsPosition) {
        positions = fortsOptionsPosition.stream()
                .map(MyFortsPosition::new)
                .collect(Collectors.toList());
    }

    public List<MyFortsPosition> getPositions() {
        return positions;
    }

    public void saveToXml() throws JAXBException {
        timeStamp = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        JAXBContext context = JAXBContextFactory.createContext(new Class[]{MyFortsPositions.class}, null);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(this,
                new File("xml/my_forts_positions.xml"));
    }

    @Override
    public String toString() {
        return "MyFortsPositions{" +
                "positions=" +
                positions.stream()
                        .map(MyFortsPosition::getFortsPosition)
                        .map(fortsPosition -> "["+fortsPosition.seccode+" * " + fortsPosition.totalnet + "]")
                        .collect(Collectors.joining(", "))
                + '}';
    }
}
