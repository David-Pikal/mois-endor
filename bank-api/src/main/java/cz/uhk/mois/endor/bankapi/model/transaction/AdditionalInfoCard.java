package cz.uhk.mois.endor.bankapi.model.transaction;

public record AdditionalInfoCard(
    String mcc,
    String merchantName,
    String cardNumber
) {}
