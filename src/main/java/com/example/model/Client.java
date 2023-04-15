package com.example.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Client {
    public int market;
    public String currency;
    public String type;
    public String forts_acc;
    @XmlAttribute
    public String id;
    @XmlAttribute
    public boolean remove;
    public String text;
}
