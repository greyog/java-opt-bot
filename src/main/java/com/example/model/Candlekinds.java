package com.example.model;

import jakarta.xml.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Candlekinds {
    @XmlElement(name = "kind")
    public List<CandleKind> kindList;

    @Override
    public String toString() {
        final String[] result = {""};
        kindList.forEach(candleKind ->{ result[0] += "[" + candleKind.name + "]";});
        return "["+result[0]+"]";
    }
//    public Candlekinds() {
//        kindList = Collections.emptyList();
//    }
}
