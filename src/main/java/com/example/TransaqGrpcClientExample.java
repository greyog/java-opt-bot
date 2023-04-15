package com.example;

import com.example.logger.MyLogger;
import com.example.model.AllData;
import com.example.model.Security;
import com.example.model.ServerStatus;
import com.example.util.*;
import com.firelib.Empty;
import com.firelib.Str;
import com.firelib.TransaqConnectorGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.commons.text.StringEscapeUtils;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TransaqGrpcClientExample implements PropertyChangeListener, TransaqClientInterface {
    private static final Logger logger = Logger.getLogger(TransaqGrpcClientExample.class.getName());

    private final ManagedChannel channel;
    private final TransaqConnectorGrpc.TransaqConnectorBlockingStub blockingStub;

    private final AllData allData = AllData.getInstance();

    public AllData getAllData() {
        return allData;
    }

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public TransaqGrpcClientExample(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build());
    }

    TransaqGrpcClientExample(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = TransaqConnectorGrpc.newBlockingStub(channel);

    }

    public String sendCommand(String command) {
        MyLogger.writeCommandResponse("[Command]:" + command);
        Str response = this.blockingStub.sendCommand(
                Str.newBuilder().setTxt(
                        command
                ).build());
        MyLogger.writeCommandResponse("[Response]:" + response.getTxt());
        return response.getTxt();
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    static String getLoginCommand(String login, String passwd, String host, String port) {

        return "<command id=\"connect\">" +
                "<login>" + login + "</login>" +
                "<password>" + passwd + "</password>" +
                "<host>" + host + "</host>" +
                "<port>" + port + "</port>" +
                "<rqdelay>100</rqdelay>" +
                "<session_timeout>1000</session_timeout> " +
                "<request_timeout>1000</request_timeout>" +
                "</command>";
    }

    private static String getDisconnectCommand() {
        return "<command id=\"disconnect\"/>";
    }

    private static String getSecurityInfoCommand(String secCode) {
        return "<command id = \"get_securities_info\">" +
            "<security>" +
            "<market>" + "4" + "</market>" +
            "<seccode>" + secCode + "</seccode>" +
            "</security>" +
            "</command>";
    }

    private static String getFortsPositionCommand(String client) {
        return "<command id = \"get_forts_positions\"/>";
    }

    private String getSubscribeForAllTradesCommand(List<Security> secList) {
        return "<command id = \"subscribe\">" +
                "<alltrades>" +
                secList.stream().map(sec -> "<security>" +
                        "<board>" + sec.board + "</board>" +
                        "<seccode>" + sec.seccode + "</seccode>" +
                        "</security>")
                        .collect(Collectors.joining("")) +
                "</alltrades>" +
                "</command>";
    }

    private String getSubscribeForQuotesCommand(List<Security> secList) {
        return "<command id = \"subscribe\">" +
                "<quotes>" +
                secList.stream().map(sec -> "<security>" +
                                "<board>" + sec.board + "</board>" +
                                "<seccode>" + sec.seccode + "</seccode>" +
                                "</security>")
                        .collect(Collectors.joining("")) +
                "</quotes>" +
                "</command>";
    }

    private static ScheduledExecutorService checkServerStatusExecutorService;


    /**
     * Greet server. If provided, the first element of {@code args} is the name to use in the
     * greeting.
     */
    public static void main(String[] args) throws Exception {
        TransaqGrpcClientExample client = new TransaqGrpcClientExample(
                "localhost", 50051);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shut down hook.");
            logger.info("Disconnecting....");
//            String disconnectRequest = getDisconnectCommand();
//            Str response = blockingStub.sendCommand(Str.newBuilder().setTxt(disconnectRequest).build());
            String response = client.sendCommand(getDisconnectCommand());
            logger.info("Disconnect response: " + response);
            logger.info("Shutting down client....");
            try {
                client.shutdown();
                logger.info("Client was shot down.");
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.toString());
            }
            logger.info("Will stop now.");
        }));

        final boolean[] isPrinted = {false};
        final int[] writerCount = {0};
        final int maxWritesToFile = 10;

        client.getAllData().addPropertyChangeListener(client);

        Strategies strategies = new Strategies(client);
//        strategies.addStrategy(new StrategyMonitorPosition());
        strategies.addStrategy(new StrategySyntheticBond());

        while (true) {
            try {
                String response = client.sendCommand(getLoginCommand(
//                        "FZTC14443A",
//                        "cvb1JV80",
                        "FZTC11978A",
                        "yD3rFfUf",
                        "tr1.finam.ru",
                        "3900"));
                String loginResponse = StringEscapeUtils.unescapeJava(response);
                System.out.println("login command response:" + loginResponse);
                if (loginResponse.equalsIgnoreCase("<result success=\"true\"/>")) {
                    startCheckingServerStatus(client);
                }
                Iterator<Str> messages = client.blockingStub.connect(Empty.newBuilder().build());
                //continuous messages, this call will generally block till the end

                MessageParser messageParser = new MessageParser();

                messages.forEachRemaining(str -> {
//                    writeToFile(writerCount, maxWritesToFile, str);
                    if (!isPrinted[0]) {
                        System.out.println("Has got server message");
                        isPrinted[0] = true;
                    }
                    try {
                        String strToParse = //                                        .append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
                                str.getTxt();
//                                .append(StringEscapeUtils.unescapeJava(str.getTxt())).toString();
//                        MyLogger.write(strToParse);

                        messageParser.parse(strToParse);
//                        parser.parse(strToParse, xmlHandler);
                    } catch (Exception e) {
                        e.printStackTrace(MyLogger.getWriter());
                    }
                });
            } catch (Exception e) {
                logger.info(e.toString());
//                e.printStackTrace();
                Thread.sleep(5000);
            }
        }
    }

    private static void startCheckingServerStatus(TransaqGrpcClientExample client) {
        checkServerStatusExecutorService = Executors.newScheduledThreadPool(1);
        checkServerStatusExecutorService.scheduleAtFixedRate(() -> {
            String response = client.sendCommand(getServerStatusCommand());
            String statusResponse = StringEscapeUtils.unescapeJava(response);
            if (statusResponse.equalsIgnoreCase("<result success=\"true\"/>")) {
                client.getAllData().setServerStatus(new ServerStatus(true));
            }
        }, 15, 5, TimeUnit.SECONDS);
    }

    private static String getServerStatusCommand() {
        return "<command id=\"server_status\"/>";
    }


    private static PrintWriter lastWriter = null;
    private static int filesCounter = 0;

    static PrintWriter getWriter(int writesCount, int maxWrites) throws FileNotFoundException {
        if (lastWriter == null) {
            lastWriter = new PrintWriter(new StringBuilder().append("messages").append(String.valueOf(filesCounter)).append(".xml").toString());
            return lastWriter;
        }
        if (writesCount > maxWrites) {
            lastWriter.close();
            filesCounter++;
            writesCount = 0;
            lastWriter = new PrintWriter(new StringBuilder()
                    .append("~/tmp/xml/messages")
                    .append(filesCounter)
                    .append(".xml")
                    .toString());
        }
        return lastWriter;
    }

    private static void writeToFile(int[] writerCount, int maxWritesToFile, Str str) {
        try {
            PrintWriter writer = getWriter(writerCount[0], maxWritesToFile);
            writerCount[0] += 1;
            writer.println(StringEscapeUtils.unescapeJava(str.getTxt()));
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if (propertyChangeEvent.getPropertyName().equals("serverStatus")) {
            if (((ServerStatus) propertyChangeEvent.getNewValue()).connected) {
                doAfterConnection();
            }
        }
    }

    private void doAfterConnection() {
        checkServerStatusExecutorService.shutdown();
//        System.out.println(sendCommand(getChangePasswordCommand("cUkBr4Wr","cvb1JV80")));
    }

    private String getChangePasswordCommand(String oldPassword, String newPassword) {
        return new StringBuilder()
                .append("<command id=\"change_pass\" ")
                .append("oldpass=\"")
                .append(oldPassword)
                .append("\" ")
                .append("newpass=\"")
                .append(newPassword)
                .append("\"")
                .append("/>")
                .toString();
    }

    @Override
    public String getFortsPosition(String client) {
        return sendCommand(getFortsPositionCommand(client));
    }

    @Override
    public String subscribeForSecAllTrades(List<Security> secList) {
        return sendCommand(getSubscribeForAllTradesCommand(secList));
    }

    @Override
    public String subscribeForSecQuotes(List<Security> secList) {
        return sendCommand(getSubscribeForQuotesCommand(secList));
    }

    @Override
    public List<String> changePositionsByMarket(Map<String, Integer> changes) {
        List<String> responses = new ArrayList<>();
        logger.info("changes"+changes.toString());
        try {
            for (Map.Entry<String, Integer> entry : changes.entrySet()) {
                logger.info("entry"+entry.getKey()+"="+entry.getValue());
                if (entry.getValue() != 0) {
                    NewOrder order = new NewOrder();
                    order.setBoard("FUT");
                    order.setSeccode(entry.getKey());
                    order.setClient(allData.getClientId());
                    order.setQuantity(entry.getValue());
                    order.setByMarket(true);
                    String command = order.getCommand();
                    logger.info("command"+command);
                    try {
                        MyLogger.writeOrder(command);
                    } catch (Exception e) {
                        logger.warning(e.toString());
                    }
                    try {
                        responses.add(sendCommand(command));
                    } catch (Exception e) {
                        logger.warning(e.toString());
                    }
                }
            }
        } catch (Exception e) {
            logger.warning(e.toString());
        }
        logger.info("responses"+responses.toString());
        return responses;
    }
}