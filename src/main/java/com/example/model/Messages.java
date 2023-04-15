package com.example.model;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
public class Messages {
    public List<Message> message;
}
