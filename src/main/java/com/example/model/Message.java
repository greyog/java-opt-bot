package com.example.model;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@XmlRootElement
public class Message {
    public Date date;
    public String urgent;
    public String from;
    public String text;
}
