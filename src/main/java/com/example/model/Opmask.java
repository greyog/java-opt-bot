package com.example.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Opmask {
    @XmlAttribute
    public String usecredit;
    @XmlAttribute
    public String bymarket;
    @XmlAttribute
    public String nosplit;
    @XmlAttribute
    public String fok;
    @XmlAttribute
    public String ioc;
    @XmlAttribute
    public String immorcancel;
    @XmlAttribute
    public String cancelbalance;
}
