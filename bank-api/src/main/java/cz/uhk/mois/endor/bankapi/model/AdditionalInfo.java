package cz.uhk.mois.endor.bankapi.model;

public class AdditionalInfo {
    private String constantSymbol;
    private String variableSymbol;
    private String specificSymbol;
    public String getConstantSymbol() {
        return constantSymbol;
    }
    public void setConstantSymbol(String constantSymbol) {
        this.constantSymbol = constantSymbol;
    }
    public String getVariableSymbol() {
        return variableSymbol;
    }
    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }
    public String getSpecificSymbol() {
        return specificSymbol;
    }
    public void setSpecificSymbol(String specificSymbol) {
        this.specificSymbol = specificSymbol;
    }
}
