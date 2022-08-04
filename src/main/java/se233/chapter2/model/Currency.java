package se233.chapter2.model;

import java.util.ArrayList;

public class Currency {
    private String shortCode;
    private CurrencyEntity currencyEntity;
    private ArrayList<CurrencyEntity> histData;
    private boolean isWatch;
    private double watchRate;
    public Currency(String shortCode) {
        this.shortCode = shortCode;
        this.isWatch = false;
        this.watchRate = 0;
    }

    public void setCurrencyEntity(CurrencyEntity currencyEntity) {
        this.currencyEntity = currencyEntity;
    }

    public ArrayList<CurrencyEntity> getHistData() {
        return histData;
    }

    public void setHistData(ArrayList<CurrencyEntity> histData) {
        this.histData = histData;
    }

    public void setWatchRate(double watchRate) {
        this.watchRate = watchRate;
    }

    public double getWatchRate() {
        return watchRate;
    }

    public boolean getWatch() {
        return isWatch;
    }

    public void setWatch(boolean watch) {
        isWatch = watch;
    }

    public CurrencyEntity getCurrencyEntity() {
        return currencyEntity;
    }

    public String getShortCode() {
        return shortCode;
    }
}
