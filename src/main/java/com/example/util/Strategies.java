package com.example.util;

import com.example.model.AllData;
import com.example.model.Alltrades;
import com.example.model.Positions;
import com.example.model.ServerStatus;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Strategies implements PropertyChangeListener {
    private final List<Strategy> strategyList = new ArrayList<>();
    private final TransaqClientInterface transaqClientInterface;

    public Strategies(TransaqClientInterface transaqClientCommandsImpl) {
        super();
        transaqClientInterface = transaqClientCommandsImpl;
        AllData.getInstance().addPropertyChangeListener(this);
    }

    public List<Strategy> getStrategyList() {
        return strategyList;
    }

    public void addStrategy(Strategy strategy) {
        strategy.setTransaqClientInterface(getTransaqClientInterface());
        this.strategyList.add(strategy);
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (Objects.equals(propertyChangeEvent.getPropertyName(), "serverStatus")) {
            if (((ServerStatus) propertyChangeEvent.getNewValue()).connected) {
                for (Strategy strategy : strategyList) {
                    strategy.setActive(true);
                }
            }
        } else if (Objects.equals(propertyChangeEvent.getPropertyName(), "allTrades")) {
            for (Strategy strategy : strategyList) {
                if (strategy.isActive()) {
                    strategy.onSecurityTrade(((Alltrades) propertyChangeEvent.getNewValue()).trade);
                }
            }
        } else if (Objects.equals(propertyChangeEvent.getPropertyName(), "positions")) {
            Positions positions = (Positions) propertyChangeEvent.getNewValue();
//            System.out.println("propertyChange positions size: " + positions.fortsPosition.size());
//            if (!positions.fortsPosition.isEmpty()) {
                for (Strategy strategy : strategyList) {
                    if (strategy.isActive()) {
                        strategy.onPositionUpdate(positions);
                    }
                }
//            }
        }
    }

    public TransaqClientInterface getTransaqClientInterface() {
        return transaqClientInterface;
    }
}
