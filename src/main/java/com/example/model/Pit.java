package com.example.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Pit {
@XmlAttribute
    public String seccode;
@XmlAttribute
    public String board;
    public int market;
    public int decimals;
    public double minstep;
    public int lotsize;
    public int lotdivider;
    public int point_cost;
    public double text;
}
