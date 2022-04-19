package cz.uhk.mois.endor.bankapi.model.transaction;

public record AdditionalInfoDomestic(
    String constantSymbol,
    String variableSymbol,
    String specificSymbol
) {}
