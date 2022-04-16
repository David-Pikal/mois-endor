package cz.uhk.mois.endor.bankapi.model.transaction;

import cz.uhk.mois.endor.bankapi.model.*;
import cz.uhk.mois.endor.bankapi.model.PartyAccount;
import lombok.Data;

import java.io.Serializable;

@Data
public class Transaction implements Serializable {
        private String id;
        private Integer accountId;
        private Value value;
        private PartyAccount partyAccount;
        private String partyDescription;
        private String direction;
        private String transactionType;
        private String valueDate;
        private String bookingDate;
        private String userDescription;
        private String payerMessage;
        private String payeeMessage;
        private Integer categoryId;
        private Integer transactionFee;
        private Boolean transactionFeeCanceled;
        private AdditionalInfoDomestic additionalInfoDomestic;
        private AdditionalInfoForeign additionalInfoForeign;
        private AdditionalInfoCard additionalInfoCard;
}
