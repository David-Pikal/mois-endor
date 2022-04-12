package cz.uhk.mois.endor.bankapi.model;

public class RecuringPayment {
    private String firstPayment;
    private String lastPayment;
    private String interval;
    public String getFirstPayment() {
        return firstPayment;
    }
    public void setFirstPayment(String firstPayment) {
        this.firstPayment = firstPayment;
    }
    public String getLastPayment() {
        return lastPayment;
    }
    public void setLastPayment(String lastPayment) {
        this.lastPayment = lastPayment;
    }
    public String getInterval() {
        return interval;
    }
    public void setInterval(String interval) {
        this.interval = interval;
    }
}
