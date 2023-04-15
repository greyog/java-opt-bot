package com.example.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {
    @XmlAttribute
    public int transactionid;
    public double orderno;
    public int secid;
    public String union;
    public String board;
    public String seccode;
    public String client;
    public String status;
    public String buysell;
    public String time;
    public String brokerref;
    public int value;
    public double accruedint;
    public String settlecode;
    public int balance;
    public int price;
    public int quantity;
    public int hidden;
    public double yield;
    public String withdrawtime;
    public String condition;
    public double maxcomission;
    public boolean within_pos;
    public String result;
    public String text;
}
