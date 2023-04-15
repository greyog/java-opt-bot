package com.example.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "server_status")
public class ServerStatus {
    @XmlAttribute
    public int sys_ver;
    @XmlAttribute
    public int build;
    @XmlAttribute
    public String server_tz;
    @XmlAttribute
    public int id;
    @XmlAttribute
    public boolean connected;

    public ServerStatus() {

    }

    public ServerStatus(boolean isConnected) {
        connected = isConnected;
    }
}
