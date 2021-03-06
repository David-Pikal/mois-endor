package cz.uhk.mois.endor.planservice.planservice.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Integer numberOfInstallments;

    @Column(nullable = false)
    private BigDecimal installment;

    @Column(nullable = false)
    private String userID;

    @Nullable
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @ManyToOne
    private Project project;

    public Loan() {}

    public Loan(BigDecimal value, Date startDate, Integer numberOfInstallments, BigDecimal installment, String userID, @Nullable Project project) {
        this.value = value;
        this.startDate = startDate;
        this.numberOfInstallments = numberOfInstallments;
        this.installment = installment;
        this.userID = userID;
        this.project = project;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public BigDecimal getInstallment() {
        return installment;
    }

    public void setInstallment(BigDecimal installment) {
        this.installment = installment;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Nullable
    public Project getProject() {
        return project;
    }

    public void setProject(@Nullable Project project) {
        this.project = project;
    }
}
