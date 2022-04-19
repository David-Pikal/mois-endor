package cz.uhk.mois.endor.bankapi.model.commons;

public record PartyAccount(
    String prefix,
    String accountNumber,
    String bankCode
) {}
