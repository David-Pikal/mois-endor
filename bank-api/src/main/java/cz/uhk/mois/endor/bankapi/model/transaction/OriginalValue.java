package cz.uhk.mois.endor.bankapi.model.transaction;

import java.math.BigDecimal;

public record OriginalValue(
    BigDecimal amount,
    String currency
) {}
