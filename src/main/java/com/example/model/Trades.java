package com.example.model;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Trades {
    public List<PositionTrade> trade = new ArrayList<>();
}
