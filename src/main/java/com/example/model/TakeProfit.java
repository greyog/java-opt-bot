package com.example.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@XmlRootElement
public class TakeProfit {
    @XmlAttribute
    public String usecredit;

    public int activationprice;
    public Date guardtime;
    public int quantity;
    public String correction;
    public String bymarket;
    public String text;
    public int extremum;
    public int level;
}
