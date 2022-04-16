package cz.uhk.mois.endor.bankapi.model.payment;

import cz.uhk.mois.endor.bankapi.model.AdditionalInfo;
import cz.uhk.mois.endor.bankapi.model.PartyAccount;
import cz.uhk.mois.endor.bankapi.model.RecuringPayment;
import cz.uhk.mois.endor.bankapi.model.Value;
import lombok.Data;

import java.io.Serializable;

@Data
public class Payment implements Serializable {
    private String _id;
    private Value value;
    private PartyAccount partyAccount;
    private String dueDate;
    private RecuringPayment recuringPayment;
    private AdditionalInfo additionalInfo;
    private Integer accountId;
    private Boolean editableByUser;
    private String realizationStatus;

}