package com.example.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Overnight {
    @XmlAttribute
    public boolean status;
}
