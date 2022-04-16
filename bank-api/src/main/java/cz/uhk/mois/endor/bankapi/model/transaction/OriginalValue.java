package cz.uhk.mois.endor.bankapi.model.transaction;

import lombok.Data;

@Data
public class OriginalValue {
    private Integer amount;
    private String currency;
}
