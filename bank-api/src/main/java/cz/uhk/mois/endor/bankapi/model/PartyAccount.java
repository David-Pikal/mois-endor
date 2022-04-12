package cz.uhk.mois.endor.bankapi.model;

public class PartyAccount {
    private String prefix;
    private String accountNumber;
    private String bankCode;
    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getBankCode() {
        return bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
