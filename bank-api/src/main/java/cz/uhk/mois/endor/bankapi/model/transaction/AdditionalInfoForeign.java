package cz.uhk.mois.endor.bankapi.model.transaction;

public record AdditionalInfoForeign(
    OriginalValue originalValue,
    Integer exchangeRate
) {}
