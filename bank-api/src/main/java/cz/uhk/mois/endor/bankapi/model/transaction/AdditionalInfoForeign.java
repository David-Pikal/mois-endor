package cz.uhk.mois.endor.bankapi.model.transaction;

import lombok.Data;

@Data
public class AdditionalInfoForeign {
    private OriginalValue originalValue;
    private Integer exchangeRate;
}
