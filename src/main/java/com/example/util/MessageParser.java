package com.example.util;

import com.example.logger.MyLogger;
import com.example.model.*;
import com.example.model.Error;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.eclipse.persistence.jaxb.JAXBContextFactory;

import java.io.StringReader;

public class MessageParser {

    Unmarshaller um = null;
    AllData allData = AllData.getInstance();

    public MessageParser(){
        initParser();
    }

    private void initParser() {
        try {
            JAXBContext jc = JAXBContextFactory.createContext(
                    new Class[]{Markets.class,
                            Boards.class,
                            Candlekinds.class,
                            Pits.class,
                            Securities.class,
                            Messages.class,
                            NewsHeader.class,
                            SecInfoUpd.class,
                            Positions.class,
                            Overnight.class,
                            ServerStatus.class,
                            Orders.class,
                            Client.class,
                            Trades.class,
                            Error.class,
                            Alltrades.class
//                            ObjectFactory.class
                    }, null);
            um = jc.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
            MyLogger.write(String.valueOf(e.getStackTrace()));
        }
    }

    private long msgCount = 0;

    public void parse(String str) throws Exception {
//        char escCode = 0x1B;
//        int row = 10;
//        int column = 5;
//        System.out.print(String.format("%c[%d;%df",escCode,row,column));
        System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
        System.out.print(new StringBuilder()
                .append("Messages received: ")
                .append(++msgCount)
//                .append("\n")
        );
        if (um == null) return;
        if (str.startsWith("<candlekinds>")) {
            allData.setCandlekinds((Candlekinds) um.unmarshal(new StringReader(str)));
            MyLogger.write("[CANDLEKINDS]: " + allData.getCandlekinds().toString());
        }
        else if (str.startsWith("<markets>")) {
            Markets markets = (Markets) um.unmarshal(new StringReader(str));
            MyLogger.write("[MARKETS]: " + markets.market.size());
        }
        else if (str.startsWith("<boards>")) {
            Boards boards = (Boards) um.unmarshal(new StringReader(str));
            MyLogger.write("[BOARDS]: " + boards.board.size());
        }
        else if (str.startsWith("<pits>")) {
//            allData.addPits( (Pits) um.unmarshal(new StringReader(str)));
//            MyLogger.write("[Pits]: " + allData.getPits().pit.size());
        }
        else if (str.startsWith("<securities>")) {
//            allData.addSecurities((Securities) um.unmarshal(new StringReader(str)));
//            MyLogger.write("[Securities]: " + allData.getSecurities().security.size());
        }
        else if (str.startsWith("<messages>")) {
            Messages messages = (Messages) um.unmarshal(new StringReader(str));
            MyLogger.write("[Messages]: " + messages.message.get(0).text);
        }
        else if (str.startsWith("<news_header>")) {
//            NewsHeader newsHeader = (NewsHeader) um.unmarshal(new StringReader(str));
//            MyLogger.write("[NewsHeader]: " + newsHeader.title);
        }
        else if (str.startsWith("<sec_info_upd>")) {
//            SecInfoUpd secInfoUpd = (SecInfoUpd) um.unmarshal(new StringReader(str));
//            MyLogger.write("[secInfoUpd]: " + secInfoUpd.seccode);
        }
        else if (str.startsWith("<positions>")) {
            MyLogger.writePositions(str);
            allData.setPositions ((Positions) um.unmarshal(new StringReader(str)));
            MyLogger.write("[Positions]: " + allData.getPositions().fortsPosition.size());
            MyLogger.write("[Money]: " + allData.getPositions().fortsMoney.free);
        }
        else if (str.startsWith("<overnight")) {
            Overnight overnight = (Overnight) um.unmarshal(new StringReader(str));
            MyLogger.write("[Overnight]: " + overnight.status);
        }
        else if (str.startsWith("<server_status")) {
            ServerStatus serverStatus = (ServerStatus) um.unmarshal(new StringReader(str));
            allData.setServerStatus(serverStatus);
            MyLogger.write("[ServerStatus]: " + serverStatus.connected);
            System.out.println("Server status: " + serverStatus.connected);
        }
        else if (str.startsWith("<orders")) {
            MyLogger.writeOrder(str);
            Orders orders = (Orders) um.unmarshal(new StringReader(str));
            MyLogger.write("[Orders]: " + orders.order.size());
        }
        else if (str.startsWith("<client")) {
            Client client = (Client) um.unmarshal(new StringReader(str));
            MyLogger.write("[Client]: " + client.id);
            allData.setClient(client);
        }
        else if (str.startsWith("<trades")) {
            MyLogger.writeTrades(str);
            Trades trades = (Trades) um.unmarshal(new StringReader(str));
            MyLogger.write("[Trades]: " + trades.trade.size());
        }
        else if (str.startsWith("<error")) {
            Error error = (Error) um.unmarshal(new StringReader(str));
            MyLogger.write("[Error]: " + error.text);
            MyLogger.write("[Error response]: " + str);
        }
        else if (str.startsWith("<alltrades")) {
            Alltrades alltrades = (Alltrades) um.unmarshal(new StringReader(str));
            allData.setAllTrades(alltrades);
//            MyLogger.write("[alltrades]: " + alltrades.trade.stream()
//                    .map(trade -> trade.seccode + " price:" + trade.price)
//                    .collect(Collectors.joining(","))
//            );
        }
        else {
            MyLogger.write("[Unknown message]: " + str);
            MyLogger.writeUnknown("[Unknown message]: " + str);
        }
//        todo implement other message types
    }
}

