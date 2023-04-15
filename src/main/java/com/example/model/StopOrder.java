package com.example.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@XmlRootElement
public class StopOrder {
    @XmlAttribute
    public int transactionid;

    public int secid;
    public String union;
    public String board;
    public String seccode;
    public String client;
    public String buysell;
    public String author;
    public Date accepttime;
    public String status;
    public StopLoss stoploss;
    public TakeProfit takeprofit;
    public Date withdrawtime;
    public String result;
    public String text;
    public double activeorderno;
    public double alltradeno;
}
