package cz.uhk.mois.endor.bankapi.model;

import lombok.Data;

@Data
public class Value {
    private Integer amount;
    private String currency;
}