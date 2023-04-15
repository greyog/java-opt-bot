package com.example.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AllData {
    private static AllData instance = null;
    private Candlekinds candlekinds = new Candlekinds();
    private final Pits pits = new Pits();
    private final Securities securities = new Securities();
    private Positions positions = new Positions();
    private ServerStatus serverStatus = new ServerStatus();
//    private SecurityInfos = new SecurityInfos();
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private Alltrades allTrades;
    private Client client;

    public static AllData getInstance() {
        if (instance == null) {
            instance = new AllData();
        }
        return instance;
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener){
        this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener){
        this.propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }

    public Pits getPits() {
        return pits;
    }

    public void addPits(Pits pits) {
        this.pits.pit.addAll(pits.pit);
    }

    public Candlekinds getCandlekinds() {
        return candlekinds;
    }

    public void setCandlekinds(Candlekinds candlekinds) {
        this.candlekinds = candlekinds;
    }

    public Securities getSecurities() {
        return securities;
    }


    public void addSecurities(Securities securities) {
        this.securities.security.addAll(securities.security);
    }

    public Positions getPositions() {
        return positions;
    }

    public void setPositions(Positions positions) {
//        System.out.println("setPositions size: " + positions.fortsPosition.size());
        propertyChangeSupport.firePropertyChange("positions", this.positions, positions);
        this.positions = positions;
    }

    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(ServerStatus serverStatus) {
        propertyChangeSupport.firePropertyChange("serverStatus", this.serverStatus, serverStatus);
        this.serverStatus = serverStatus;
    }

    public void setAllTrades(Alltrades allTrades) {
        propertyChangeSupport.firePropertyChange("allTrades", this.allTrades, allTrades);
        this.allTrades = allTrades;
    }

    public Alltrades getAllTrades() {
        return allTrades;
    }

    public String getClientId() {
        return client.id;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
