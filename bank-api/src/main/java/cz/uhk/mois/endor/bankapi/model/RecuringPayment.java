package cz.uhk.mois.endor.bankapi.model;

import lombok.Data;

@Data
public class RecuringPayment {
    private String firstPayment;
    private String lastPayment;
    private String interval;
}
