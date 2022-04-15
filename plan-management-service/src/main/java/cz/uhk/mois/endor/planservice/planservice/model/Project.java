package cz.uhk.mois.endor.planservice.planservice.model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer userID;

    @Column(nullable = false)
    private Date startDate;

    @Nullable
    private Date endDate;

    @Nullable
    private BigDecimal value;

    @OneToMany(mappedBy = "project")
    private List<Payment> payments;

    @OneToMany(mappedBy = "project")
    private List<Loan> loans;

    public Project () {}

    public Project(String title, Integer userID, Date startDate, @Nullable Date endDate, @Nullable BigDecimal value) {
        this.title = title;
        this.userID = userID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.value = value;
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

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Nullable
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@Nullable Date endDate) {
        this.endDate = endDate;
    }

    @Nullable
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(@Nullable BigDecimal value) {
        this.value = value;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
