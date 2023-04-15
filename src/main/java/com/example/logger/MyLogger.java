package com.example.logger;

import com.google.type.DateTime;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.Date;

public class MyLogger {
    private static PrintWriter writerLog = null;
    private static PrintWriter writerUnknown = null;
    private static PrintWriter writerOrders = null;
    private static PrintWriter writerTrades = null;
    private static PrintWriter writerPositions = null;

    public static PrintWriter getWriter() {
        if (writerLog == null) {
            writerLog = initWriter("logs/logs.log");
        }
        return writerLog;
    }

    private static PrintWriter getWriterUnknown() {
        if (writerUnknown == null) {
            writerUnknown = initWriter("logs/unknown.xml");
        }
        return writerUnknown;
    }

    private static void writeWithWriter(PrintWriter printWriter, String str, boolean trim) {
        if (printWriter != null) {
            String strToOut = trim ? str.substring(0, Math.min(str.length(), 100)) : str;

            printWriter.println(new StringBuilder().
                    append("[").
                    append(Date.from(Instant.now())).
                    append("]").
                    append(strToOut));
            printWriter.flush();
        }
    }

    private static synchronized void writeWithNio(String filename, String content, boolean trim) {
        String strToOut = trim ? content.substring(0, Math.min(content.length(), 100)) : content;
        StringBuilder builder = new StringBuilder().
                append("[").
                append(DateFormatUtils.format(System.currentTimeMillis(), "HH:mm:ss")).
                append("]").
                append(strToOut).append("\n");
        try {
            Files.write(Path.of(new StringBuilder().
                            append("logs/").
                            append(DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd")).
                            append(" ").
                            append(filename).
                            toString()),
                    builder.toString().getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String str) {
//        writeWithWriter(getWriter(), str, true);
        writeWithNio("logs.log", str, true);
    }

    public static void writeUnknown(String str) {
//        writeWithWriter(getWriterUnknown(), str, false);
        writeWithNio("unknown.xml", str, false);
    }

    public static void writeOrder(String s) {
        writeWithNio("orders.xml", s, false);
//        writeWithWriter(getWriterOrders(), s, false);
    }

    public static void writeTrades(String s) {
        writeWithNio("trades.xml", s, false);
//        writeWithWriter(getWriterTrades(), s, false);
    }

    public static void writePositions(String s) {
        writeWithNio("positions.xml", s, false);
//        writeWithWriter(getWriterPositions(), s, false);
    }
    private static PrintWriter initWriter(String s) {
        try {
            PrintWriter writer = new PrintWriter(s);
            return writer;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PrintWriter getWriterOrders() {
        if (writerOrders == null) {
            writerOrders = initWriter("logs/orders.xml");
        }
        return writerOrders;
    }


    private static PrintWriter getWriterTrades() {
        if (writerTrades == null) {
            writerTrades = initWriter("logs/trades.xml");
        }
        return writerTrades;
    }

    private static PrintWriter getWriterPositions() {
        if (writerPositions == null) {
            writerPositions = initWriter("logs/positions.xml");
        }
        return writerPositions;

    }

    public static void writeCommandResponse(String s) {
        writeWithNio("command_response.xml", s, false);
    }
}
