package cz.uhk.mois.endor.bankapi.model.payment;

import cz.uhk.mois.endor.bankapi.model.commons.AdditionalInfo;
import cz.uhk.mois.endor.bankapi.model.commons.PartyAccount;
import cz.uhk.mois.endor.bankapi.model.commons.Value;

public record Payment (
    String _id,
    Value value,
    PartyAccount partyAccount,
    String dueDate,
    RecuringPayment recuringPayment,
    AdditionalInfo additionalInfo,
    Integer accountId,
    Boolean editableByUser,
    String realizationStatus
) {}