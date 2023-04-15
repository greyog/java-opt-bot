package com.example.model;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CandleKind {
    public int id;
    public int period;
    public String name;

//    public CandleKind(int id, int period, String name) {
//        this.id = id;
//        this.period = period;
//        this.name = name;
//    }
}

