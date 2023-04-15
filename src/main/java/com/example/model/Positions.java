package com.example.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Positions {
    @XmlElement(name = "forts_position")
    public List<FortsPosition> fortsPosition = new ArrayList<>();
    @XmlElement(name = "forts_money")
    public FortsMoney fortsMoney;
}
