package com.example.util;

import com.example.model.Security;

import java.util.List;
import java.util.Map;

public interface TransaqClientInterface {
    String getFortsPosition(String client);

    String subscribeForSecAllTrades(List<Security> secList); // все сделки
    String subscribeForSecQuotes(List<Security> secList); // изменения стакана заявок

    List<String> changePositionsByMarket(Map<String, Integer> changeToHedge);

//    String get
}
