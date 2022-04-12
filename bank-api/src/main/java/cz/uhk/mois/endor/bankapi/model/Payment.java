package cz.uhk.mois.endor.bankapi.model;

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