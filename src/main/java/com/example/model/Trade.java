package com.example.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Trade {
    public double tradeno;
    public String board;
    public String time;
    public int price;
    public int quantity;
    public String buysell;
    public int openinterest;
    public String seccode;
    public String period;
    @XmlAttribute
    public int secid;
    public String text;
}
