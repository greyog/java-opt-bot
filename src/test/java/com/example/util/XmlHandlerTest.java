package com.example.util;

import junit.framework.TestCase;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class XmlHandlerTest extends TestCase {
    SAXParser parser;
    SAXParserFactory factory;
    XmlHandler xmlHandler;
    public void setUp() throws Exception {
        factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        parser = factory.newSAXParser();
        xmlHandler = new XmlHandler();
    }

    public void testStartElement() throws Exception {
        File file = new File("/home/greyog/tmp/xml_0/0candles.xml");
        parser.parse(file, xmlHandler);
        assert !xmlHandler.readedUri.equals("");
    }
}