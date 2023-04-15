package com.example.util;

import com.example.model.Boards;
import com.example.model.Candlekinds;
import com.example.model.Markets;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;

import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    @XmlElementDecl(name = "candlekinds")
    public JAXBElement<Candlekinds> createCandlekinds(Candlekinds candlekinds) {
        return new JAXBElement<Candlekinds>(new QName("candlekinds"), Candlekinds.class, candlekinds);
    }

    @XmlElementDecl(name = "markets")
    public JAXBElement<Markets> createMarkets(Markets element) {
        return new JAXBElement<Markets>(new QName("markets"), Markets.class, element);
    }

    @XmlElementDecl(name = "boards")
    public JAXBElement<Boards> createBoards(Boards element) {
        return new JAXBElement<Boards>(new QName("boards"), Boards.class, element);
    }
}
