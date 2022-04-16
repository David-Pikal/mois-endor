package cz.uhk.mois.endor.bankapi.model.transaction;

import lombok.Data;

@Data
public class AdditionalInfoDomestic {
    private String constantSymbol;
    private String variableSymbol;
    private String specificSymbol;
}
