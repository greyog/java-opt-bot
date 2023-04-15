package com.example.util;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MessageParserTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testParse() throws Exception {
        List<String> file = Files.readAllLines(Path.of("/home/greyog/tmp/xml_0/0markets_boards.xml"));
        StringBuilder stringBuilder = new StringBuilder();
        file.forEach(stringBuilder::append);
        MessageParser messageParser = new MessageParser();
        messageParser.parse(stringBuilder.toString());
    }
}