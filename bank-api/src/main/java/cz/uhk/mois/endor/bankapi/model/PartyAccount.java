package cz.uhk.mois.endor.bankapi.model;

import lombok.Data;

@Data
public class PartyAccount {
    private String prefix;
    private String accountNumber;
    private String bankCode;
}
