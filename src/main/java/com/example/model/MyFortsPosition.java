package com.example.model;

import com.example.util.OptionCodeParser;
import com.example.util.OptionType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MyFortsPosition {
    @XmlElement
    private FortsPosition fortsPosition;
    @XmlElement
    private double startPrice = 0;

    private String assetCode;
    private boolean isWeekOption;
    private OptionType optionType;
    private String futuresCode;
    private int strike;

    public MyFortsPosition(){};

    public MyFortsPosition(FortsPosition fortsPosition) {
        this.fortsPosition = fortsPosition;
        OptionCodeParser.parseFortsPosition(this);
    }

    public FortsPosition getFortsPosition() {
        return fortsPosition;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setIsWeekOption(boolean isWeekOption) {
        this.isWeekOption = isWeekOption;
    }

    public boolean getIsWeekOption() {
        return isWeekOption;
    }

    public void setOptionType(String optionType) {
        if( optionType.equalsIgnoreCase(OptionType.CALL.name())) {
            this.optionType = OptionType.CALL;
        } else if( optionType.equalsIgnoreCase(OptionType.PUT.name())) {
            this.optionType = OptionType.PUT;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public OptionType getOptionType() {
        return optionType;
    }

    public void setFuturesCode(String futuresCode) {
        this.futuresCode = futuresCode;
    }

    public String getFuturesCode() {
        return futuresCode;
    }

    public void setStrike(int strike) {
        this.strike = strike;
    }

    public int getStrike() {
        return strike;
    }
}
