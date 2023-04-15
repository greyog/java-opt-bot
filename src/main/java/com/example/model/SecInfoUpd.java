package com.example.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sec_info_upd")
public class SecInfoUpd {
    public int secid;
    public String seccode;
    public int market;
    public double buy_deposit;
    public double sell_deposit;
    public int point_cost;
    public int minprice;
    public int maxprice;
}
