package com.example.util;

import com.example.model.Positions;
import com.example.model.Trade;

import java.util.List;
import java.util.logging.Logger;

public abstract class Strategy {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    private boolean isActive = false;
    private TransaqClientInterface transaqClientInterface;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        if (!isActive && active) {
            onStart();
        } else if (isActive && !active) {
            onStop();
        }
        isActive = active;
    }

    public abstract void onStart();
    public abstract void onStop();
    public abstract void onSecurityTrade(List<Trade> trades);
    public abstract void onPositionUpdate(Positions positions);

    public void setTransaqClientInterface(TransaqClientInterface transaqClientInterface) {
        this.transaqClientInterface = transaqClientInterface;
    }

    public TransaqClientInterface getTransaqClient() {
        return transaqClientInterface;
    }

}
