package com.example.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Security {
    public String sec_tz;
    public String seccode;
    public String instrclass;
    public String currency;
    public String board;
    public String shortname;
    public int decimals;
    public int market;
    public String sectype;
    public Opmask opmask;
    public double minstep;
    public int lotsize;
    public int lotdivider;
    public int point_cost;
    public int quotestype;
    @XmlAttribute
    public int secid;
    @XmlAttribute
    public boolean active;
    public String text;
}
