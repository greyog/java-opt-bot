package com.example.util;

public interface IBasicStrategy {
//    void onAccountUpdate();// to receive updates about account
    void onTickerUpdate();// to receive new tickers
    void onOrderUpdate();// to receive updates about orders
    void onTradeUpdate(); //to receive updates about trades
    void onPositionUpdate();// to receive updates about positions
    void onPositionStatusUpdate();// to receive updates about position status change
}
