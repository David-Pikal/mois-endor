package cz.uhk.mois.endor.bankapi.model.commons;

import java.math.BigDecimal;

public record Value(
        BigDecimal amount,
        String currency
) {}