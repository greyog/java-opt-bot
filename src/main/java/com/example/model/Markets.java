package com.example.model;

import java.util.List;
import jakarta.xml.bind.annotation.*;

@XmlRootElement
public class Markets {
    @XmlElement
    public List<Market> market;
}
