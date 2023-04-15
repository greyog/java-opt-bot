package com.example.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "trade")
public class PositionTrade {
    public int secid;
    public double tradeno;
    public double orderno;
    public String board;
    public String seccode;
    public String client;
    public String buysell;
    public String union;
    public String time;
    public String brokerref;
    public int value;
    public double comission;
    public int price;
    public int quantity;
    public int items;
    public double yield;
    public int currentpos;
    public double accruedint;
    public String tradetype;
    public String settlecode;
}
