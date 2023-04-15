package com.example.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "news_header")
public class NewsHeader {
    public int id;
    public String timestamp;
    public String source;
    public String title;
}
