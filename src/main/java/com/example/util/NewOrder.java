package com.example.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class NewOrder {
    private String seccode;
    private String board;
    private String client;
    private String union;
    private double price;
    private int hidden;
    private int quantity;
    private String buysell = "B"; //B or S
    public enum BuySell {
        B,S
    }
    private boolean byMarket;
    private String brokerRef;
    private String unfilled = "IOC";
    //    PutInQueue: неисполненная часть заявки помещается в очередь заявок Биржи.
//    FOK: сделки совершаются только в том случае, если заявка может быть удовлетворена полностью.
//    IOC: неисполненная часть заявки снимается с торгов
    public enum Unfilled {
        PutInQueue, FOK, IOC
    }
    private boolean useCredit;
    private boolean noSplit;
    private String expDate;
    //    (задается в формате 23.07.2012 00:00:00 (не обязательно))

    public void setSeccode(String seccode) {
        this.seccode = seccode;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setHidden(int hidden) {
        this.hidden = hidden;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            buysell = "B";
        } else {
            buysell = "S";
        }
        this.quantity = Math.abs( quantity);
    }

    public void setByMarket(boolean byMarket) {
        this.byMarket = byMarket;
    }

    public void setBrokerRef(String brokerRef) {
        this.brokerRef = brokerRef;
    }

    public void setUnfilled(String unfilled) {
        this.unfilled = unfilled;
    }

    public void setUseCredit(boolean useCredit) {
        this.useCredit = useCredit;
    }

    public void setNoSplit(boolean noSplit) {
        this.noSplit = noSplit;
    }


    public void setExpDate(Date expDate) {
        //    (задается в формате 23.07.2012 00:00:00 (не обязательно))
        this.expDate = DateFormatUtils.format(expDate, "dd.MM.yyyy HH:mm:ss");
    }

    public String getCommand() {
        StringBuilder builder = new StringBuilder()
                .append("<command id=\"neworder\">")
                .append("<security>")
                    .append("<board>")
                        .append(board)
                    .append("</board>")
                    .append("<seccode>")
                        .append(seccode)
                    .append("</seccode>")
                .append("</security>")
                .append("<client>")
                    .append(client)
                .append("</client>");
        if (union != null) {
            builder.append("<union>")
                    .append(union)
                    .append("</union>");
        }
        if (price != 0) {
            builder.append("<price>")
                    .append(price)
                    .append("</price>");
        }
        if (hidden != 0) {
            builder.append("<hidden>")
                    .append(hidden)
                    .append("</hidden>");
        }
        builder.append("<quantity>")
                .append(quantity)
                .append("</quantity>");
        builder.append("<buysell>")
                .append(buysell)
                .append("</buysell>");
        if (byMarket) {
            builder.append("<bymarket/>");
        }
        if (brokerRef != null) {
            builder.append("<brokerref>")
                    .append(brokerRef)
                    .append("</brokerref>");
        }
        builder.append("<unfilled>")
                .append(unfilled.toString())
                .append("</unfilled>");
        if (useCredit) {
            builder.append("<usecredit/>");
        }
        if (noSplit) {
            builder.append("<nosplit/>");
        }
        if (expDate != null) {
            builder.append("<expdate>")
                    .append(expDate)
                    .append("</expdate>");
        }
        builder.append("</command>");
        return builder.toString();
    }
}
