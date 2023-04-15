package com.example.model;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class Alltrades {
    public List<Trade> trade;
}
