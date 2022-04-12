package cz.uhk.mois.endor.bankapi.model;

public class Value {
    private Integer amount;
    private String currency;
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}