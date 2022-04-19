package cz.uhk.mois.endor.planservice.planservice.controller;

import cz.uhk.mois.endor.planservice.planservice.model.Loan;
import cz.uhk.mois.endor.planservice.planservice.model.Project;
import cz.uhk.mois.endor.planservice.planservice.repository.LoanRepository;
import cz.uhk.mois.endor.planservice.planservice.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "/loan")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping(path = "/add")
    public String addNewLoan(@RequestParam String value, @RequestParam String startDate, @RequestParam Integer userID,
                                @RequestParam Integer numberOfInstallments, @RequestParam String installment, @RequestParam(required = false) Integer projectID) {

        Loan loan = new Loan(new BigDecimal(value), Date.valueOf(startDate), numberOfInstallments, new BigDecimal(installment), userID, getProject(projectID));
        loanRepository.save(loan);

        return "Saved";
    }

    @PutMapping(path = "/update")
    public String updateLoan(@RequestParam Integer id, @RequestParam String value, @RequestParam String startDate,
                                @RequestParam Integer numberOfInstallments, @RequestParam String installment) {

        Optional<Loan> optional = loanRepository.findById(id);
        if (optional.isEmpty()) {
            return "Id does not exist";
        }

        Loan loan = optional.get();

        loan.setValue(new BigDecimal(value));
        loan.setStartDate(Date.valueOf(startDate));
        loan.setNumberOfInstallments(numberOfInstallments);
        loan.setInstallment(new BigDecimal(installment));

        loanRepository.save(loan);

        return "Saved";
    }

    @GetMapping(path = "/all")
    public Iterable<Loan> getAllUserLoans(@RequestParam Integer userID) {
        return loanRepository.findByUserID(userID);
    }

    @GetMapping(path = "/project")
    public Iterable<Loan> getUserProjectLoans(@RequestParam Integer projectID) {
        Project project = getProject(projectID);
        if (project == null) {
            return null;
        }

        return loanRepository.findByProject(project);
    }

    @GetMapping(path = "/one")
    public Optional<Loan> getOneUserLoan(@RequestParam Integer id) {
        return loanRepository.findById(id);
    }

    @DeleteMapping(path = "/delete")
    public String deleteUserLoan(@RequestParam Integer id) {
        try {
            loanRepository.deleteById(id);
            return "Deleted";
        } catch (EmptyResultDataAccessException e) {
            return "Id does not exist";
        }
    }

    private Project getProject(Integer id) {
        if (id != null && id > 0) {
            Optional<Project> optional = projectRepository.findById(id);
            if (optional.isPresent()) {
                return optional.get();
            }
        }
        return null;
    }
}
