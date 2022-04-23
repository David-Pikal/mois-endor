package cz.uhk.mois.endor.bankapi.model.transaction;

import cz.uhk.mois.endor.bankapi.model.commons.PartyAccount;
import cz.uhk.mois.endor.bankapi.model.commons.Value;

public record Transaction(
        String id,
        Integer accountId,
        Value value,
        PartyAccount partyAccount,
        String partyDescription,
        Direction direction,
        String transactionType,
        String valueDate,
        String bookingDate,
        String userDescription,
        String payerMessage,
        String payeeMessage,
        Integer categoryId,
        Integer transactionFee,
        Boolean transactionFeeCanceled,
        AdditionalInfoDomestic additionalInfoDomestic,
        AdditionalInfoForeign additionalInfoForeign,
        AdditionalInfoCard additionalInfoCard
) {}
