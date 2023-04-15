package com.example.util;

import com.example.model.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StrategyMonitorPosition extends Strategy {
//    private final AllData allData = AllData.getInstance();
    private volatile Map<String, Integer> targetFuturesToTrade;
    private volatile boolean canTrade;
    private volatile Set<String> futuresToWatch = new HashSet<>();
    private volatile MyFortsPositions myOptionsPositions;
    private final Set<String> assetCodeFilter = Stream.of("Si").map(String::toLowerCase).collect(Collectors.toSet());
//    private Positions fortsPosition;
    private Map<String, Integer> futuresPosition;

    @Override
    public void onStart() {
//        logger.info(this.getClass().getName() + " onStart");
//        printPositions();
//        printSiSecurities();
//        scheduleTradesUpdate(5);
        logger.info("");
        getTransaqClient().getFortsPosition("");
        canTrade = true;
    }

    private void subscribeForFuturesFromOptionsPosition(Positions positions) {

        List<FortsPosition> fortsOptionsPosition = getOptionsListFiltered(positions);

        myOptionsPositions = new MyFortsPositions(fortsOptionsPosition);

        Set<String> freshFuturesToWatch = getMyFortsPositionFilteredStream()
                .map(MyFortsPosition::getFuturesCode)
                .collect(Collectors.toSet());

        freshFuturesToWatch.removeAll(futuresToWatch);
        if (!freshFuturesToWatch.isEmpty()) {
            futuresToWatch.addAll(freshFuturesToWatch);
            logger.info("futuresToWatch: "+futuresToWatch.toString());
            getTransaqClient().subscribeForSecAllTrades(
                    futuresToWatch.stream()
                    .map(secCode -> {
                        Security sec = new Security();
                        sec.seccode = secCode;
                        sec.board = "FUT";
                        return sec;
                    }).collect(Collectors.toList())
            );
        }
    }

    private List<FortsPosition> getOptionsListFiltered(Positions positions) {
        return getOptionsList(positions).stream()
                .filter(fp -> assetCodeFilter.contains(fp.getSecCode().substring(0,2).toLowerCase()))
                .collect(Collectors.toList());
    }

    private Stream<MyFortsPosition> getMyFortsPositionFilteredStream() {
        return myOptionsPositions.getPositions().stream()
                .filter(mfp -> assetCodeFilter.contains(mfp.getAssetCode().toLowerCase()));
    }

    private synchronized Map<String, Integer> getFuturesPosition(Positions positions) {
        return positions.fortsPosition.stream()
                .filter(fortsPosition -> fortsPosition.seccode.length() == 4)
                .collect(Collectors.toMap(FortsPosition::getSecCode, FortsPosition::getTotalNet));
    }

    private synchronized Map<String, Integer> getFuturesPositionFiltered(Positions positions) {
        Map<String, Integer> allFuturesPosition = getFuturesPosition(positions);
//        logger.info("allFuturesPosition: " + allFuturesPosition.toString());
        return allFuturesPosition.entrySet().stream()
                .filter(entry -> assetCodeFilter.contains(entry.getKey().substring(0,2).toLowerCase()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<FortsPosition> getOptionsList(Positions positions) {
       return positions.fortsPosition.stream()
                .filter(fortsPosition -> fortsPosition.seccode.length()>4)
                .collect(Collectors.toList());
    }

    @Override
    public void onStop() {
// cancel all orders
    }

    @Override
    public void onSecurityTrade(List<Trade> trades) {
        if (canTrade) {
            targetFuturesToTrade = getTargetFuturesToHedge(trades);
//            logger.info("target:"+targetFuturesToHedge.toString());
//            logger.info("futuresPosition:"+futuresPosition.toString());
            Map<String, Integer> changeToTrade = calcChangesFrom(targetFuturesToTrade, futuresPosition);
//            logger.info("target:"+targetFuturesToHedge.toString()+"; change:"+changeToTrade.toString());
            if (changeToTrade.isEmpty()) return;
            logger.info("target:"+ targetFuturesToTrade.toString()+"; change:"+changeToTrade.toString());
            getTransaqClient().changePositionsByMarket(changeToTrade);
            canTrade = false;
            logger.info("can trade: "+ canTrade);

        }
//        logger.info(changeToHedge.toString());
    }

    @Override
    public void onPositionUpdate(Positions positions) {
//                    logger.info("can trade: "+ canTrade);
//        fortsPosition = positions;
        subscribeForFuturesFromOptionsPosition(positions);

//        Map<String, Integer> freshFuturesPos = getFuturesPosition(positions);
        futuresPosition = getFuturesPositionFiltered(positions);

        logger.info("target:"+ targetFuturesToTrade.toString()+"; fresh:"+futuresPosition.toString());
        if (futuresPosition.equals(targetFuturesToTrade)) {
            canTrade = true;
        }
        logger.info("can trade: "+ canTrade);
    }

    private Map<String, Integer> calcChangesFrom(Map<String, Integer> target, Map<String, Integer> old) {
        Set<String> keys = new HashSet<>();
        keys.addAll(target.keySet());
        keys.addAll(old.keySet());
        Map<String, Integer> result = new HashMap<>();
        for (String key : keys) {
            int t = target.getOrDefault(key, 0);
            int o = old.getOrDefault(key, 0);
            int q = t - o;
            if (q != 0) {
                result.put(key, q);
            }
        }
        return result;
    }

    private Map<String, Integer> getTargetFuturesToHedge(List<Trade> trades) {
        Map<String, Integer> targetFuturesToHedge = new HashMap<>();
        for (MyFortsPosition p : getMyFortsPositionFilteredStream().collect(Collectors.toList())) {
//            logger.info("myOptionsPositions.getPositions() size : " + myOptionsPositions.getPositions().size());
            Trade t = trades.get(trades.size() - 1);
//            logger.info("trade : " + t.seccode+"*"+t.price);
//            if (t == null) {
//                continue;
//            }

            int targetFuturesCount = 0;
            if (p.getOptionType() == OptionType.CALL && p.getFortsPosition().totalnet < 0) {
                if (t.price > p.getStrike()) {
                    targetFuturesCount += Math.abs(p.getFortsPosition().totalnet);
                }
            } else if (p.getOptionType() == OptionType.PUT && p.getFortsPosition().totalnet < 0) {
                if (t.price < p.getStrike()) {
                    targetFuturesCount -= Math.abs(p.getFortsPosition().totalnet);
                }
            }
            int prevFuturesCount = targetFuturesToHedge.getOrDefault(p.getFuturesCode(), 0);
            targetFuturesToHedge.put(p.getFuturesCode(), prevFuturesCount + targetFuturesCount);
        }
        return targetFuturesToHedge;
    }
}
