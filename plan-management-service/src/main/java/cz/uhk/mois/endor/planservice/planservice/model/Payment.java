package cz.uhk.mois.endor.planservice.planservice.model;

import com.sun.istack.NotNull;
import cz.uhk.mois.endor.planservice.planservice.util.Cycle;
import cz.uhk.mois.endor.planservice.planservice.util.PaymentType;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
  Entity represents regular payment (income or expense)
 */
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private Date startDate;

    @Nullable
    private Date endDate;

    @Column(nullable = false)
    private Integer userID;

    @Column(nullable = false)
    private Cycle cycle;

    @Column(nullable = false)
    private PaymentType paymentType;

    @Nullable
    @ManyToOne
    private Project project;


    public Payment() {}

    public Payment(String title, BigDecimal value, Date startDate, @Nullable Date endDate, Integer userID, Cycle cycle, PaymentType paymentType, @Nullable Project project) {
        this.title = title;
        this.value = value;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userID = userID;
        this.cycle = cycle;
        this.paymentType = paymentType;
        this.project = project;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Nullable
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@Nullable Date endDate) {
        this.endDate = endDate;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public void setCycle(Cycle cycle) {
        this.cycle = cycle;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Nullable
    public Project getProject() {
        return project;
    }

    public void setProject(@Nullable Project project) {
        this.project = project;
    }
}
