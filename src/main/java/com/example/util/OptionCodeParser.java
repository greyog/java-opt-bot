package com.example.util;

import com.example.model.FortsPosition;
import com.example.model.MyFortsPosition;

import java.util.List;

public class OptionCodeParser {
    //    private static final Logger logger = new Logger(Scratch.class.getName());
    private static final List<String> weeks = List.of("A","B","C","D","E");
    private static final List<String> monthCall = List.of("A","B","C","D","E","F","G","H","I","J","K","L");
    private static final List<String> monthPut = List.of("M","N","O","P","Q","R","S","T","U","V","W","X");
    private static final List<String> monthFutures = List.of("F","G","H","J","K","M","N","Q","U","V","X","Z");

    private static String getFuturesMonthCode(int optionMonthNum) {
        if (optionMonthNum <= 3) return monthFutures.get(2);
        else if (optionMonthNum <= 6) return monthFutures.get(5);
        else if (optionMonthNum <= 9) return monthFutures.get(8);
        else return monthFutures.get(11);
    }

    private static String getLast(String str) {
        String substring = str.substring(str.length() - 1);
//        System.out.println("Last character: " + substring);
        return substring;
    }

    private static String trimLastChar(String str) {
        String substring = str.substring(0, str.length() - 1);
//        System.out.println("After trimming last: " + substring);
        return substring;
    }

    public static void parseFortsPosition(MyFortsPosition myFortsPosition) {
//        String seccode = "SR26000BB2B";
//        String seccode = "RI120000BN2A";
        String seccode = myFortsPosition.getFortsPosition().seccode;
        String assetCode = seccode.substring(0,2);

        myFortsPosition.setAssetCode(assetCode);

//        System.out.println("Asset code: " + assetCode);
        seccode = seccode.substring(2, seccode.length());
//        System.out.println("seccode: " + seccode);

        String lastChar = getLast(seccode);
        seccode = trimLastChar(seccode);
        boolean isWeekOption = lastChar.matches("[A-E]");

        myFortsPosition.setIsWeekOption(isWeekOption);

//        System.out.println("Is week option: " + isWeekOption);
        if (isWeekOption) {
            String week = lastChar;
//            System.out.println("Week code: " + week);
            int weekNum = weeks.indexOf(week) + 1;
//            System.out.println("Week number: " + weekNum);
        }
        String yearDigit = lastChar;
        if (isWeekOption) {
            yearDigit = getLast(seccode);
            seccode = trimLastChar(seccode);
        }

//        System.out.println("Year code: " + yearDigit);

        String month = getLast(seccode);
        seccode = trimLastChar(seccode);
        boolean isCallOption = month.matches("[A-L]");
        boolean isPutOption = month.matches("[M-X]");


        int monthNum;
        if (isCallOption) {
//            System.out.println("Option type: " + "CALL");
            monthNum = monthCall.indexOf(month) + 1;
            myFortsPosition.setOptionType("call");
//            System.out.println("Month number: " + monthNum);
        }
        else {
//            System.out.println("Option type: " + "PUT");
            monthNum = monthPut.indexOf(month) + 1;
            myFortsPosition.setOptionType("put");
//            System.out.println("Month number: " + monthNum);
        }
        String futType = getLast(seccode);
        seccode = trimLastChar(seccode);
//        System.out.println("Futures type: " + futType);

        String futuresCode = assetCode + getFuturesMonthCode(monthNum) + yearDigit;

        myFortsPosition.setFuturesCode(futuresCode);
//        System.out.println("Futures code: " + futuresCode);

        int strike = Integer.parseInt(seccode);
        myFortsPosition.setStrike(strike);
//        System.out.println("Strike price: " + strike);
    }
}
