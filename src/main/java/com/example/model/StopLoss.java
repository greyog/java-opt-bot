package com.example.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@XmlRootElement
public class StopLoss {
    @XmlAttribute
    public String usecredit;

    public int activationprice;
    public Date guardtime;
    public int quantity;
    public String bymarket;
    public Double orderprice;
    public String text;
}
