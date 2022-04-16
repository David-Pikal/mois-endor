package cz.uhk.mois.endor.bankapi.model.transaction;

import lombok.Data;

@Data
public class AdditionalInfoCard {
    private String mcc;
    private String merchantName;
    private String cardNumber;
}
