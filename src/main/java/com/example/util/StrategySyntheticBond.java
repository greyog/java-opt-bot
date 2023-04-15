package com.example.util;

import com.example.model.Positions;
import com.example.model.Security;
import com.example.model.Trade;

import java.util.ArrayList;
import java.util.List;

public class StrategySyntheticBond extends Strategy{
    @Override
    public void onStart() {
        Security security = new Security();
        security.seccode = "SBER";
        security.board = "TQBR";
        List<Security> securities = new ArrayList<>();
        securities.add(security);

        getTransaqClient().subscribeForSecQuotes(securities);
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSecurityTrade(List<Trade> trades) {

    }

    @Override
    public void onPositionUpdate(Positions positions) {

    }
}
