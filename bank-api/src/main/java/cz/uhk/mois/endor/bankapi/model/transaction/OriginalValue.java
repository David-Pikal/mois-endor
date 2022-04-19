package cz.uhk.mois.endor.bankapi.model.transaction;

public record OriginalValue(
    Integer amount,
    String currency
) {}
