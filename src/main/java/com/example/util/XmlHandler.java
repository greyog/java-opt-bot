package com.example.util;

import com.example.logger.MyLogger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XmlHandler extends DefaultHandler {
    private SAXParser parser = null;
    public String readedUri = null;
    private String lastRootElement = null;
    private long myId = System.currentTimeMillis();

    public XmlHandler() {
        try {
            parser = SAXParserFactory.newInstance().newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            MyLogger.write(e.getMessage());
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (lastRootElement.equals(qName)) {
            lastRootElement = null;
            String out = (new StringBuilder()
                    .append("myId: ")
                    .append(myId)
                    .append(". Root element: ")
                    .append(qName).toString());
            System.out.println(out);
            MyLogger.write(out);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (lastRootElement == null) {
            lastRootElement = qName;
        }
//        if (Objects.equals(qName, "candlekinds")) {
//            System.out.println("candlekinds, uri: ");
//            System.out.println(uri);
//            readedUri = uri;
//            try {
//                parser.parse(uri, new CandleXmlHandler());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
