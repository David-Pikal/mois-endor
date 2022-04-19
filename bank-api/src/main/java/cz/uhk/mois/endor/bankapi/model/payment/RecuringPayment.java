package cz.uhk.mois.endor.bankapi.model.payment;

public record RecuringPayment(
    String firstPayment,
    String lastPayment,
    String interval
) {}
