package se233.chapter2.model;

public class CurrencyEntity {
    private double rate;
    private String date;

    public CurrencyEntity(double rate, String date) {
        this.rate = rate;
        this.date = date;
    }
    public double getRate() {
        return this.rate;
    }
    public String getDate() {
        return this.date;
    }
    public String toString() {
        return String.format("%s %.4f", date, rate);
    }
}
