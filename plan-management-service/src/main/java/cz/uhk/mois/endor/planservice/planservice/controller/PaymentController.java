package cz.uhk.mois.endor.planservice.planservice.controller;

import cz.uhk.mois.endor.planservice.planservice.model.Project;
import cz.uhk.mois.endor.planservice.planservice.repository.PaymentRepository;
import cz.uhk.mois.endor.planservice.planservice.model.Payment;
import cz.uhk.mois.endor.planservice.planservice.repository.ProjectRepository;
import cz.uhk.mois.endor.planservice.planservice.util.Cycle;
import cz.uhk.mois.endor.planservice.planservice.util.DateParser;
import cz.uhk.mois.endor.planservice.planservice.util.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

@RestController
@RequestMapping(path="/plan/payment")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping(path = "/add")
    public String addNewPayment(@RequestParam String title, @RequestParam String value, @RequestParam String startDate, @RequestParam String endDate,
                                @RequestParam String userID, @RequestParam String cycle, @RequestParam String paymentType, @RequestParam(required = false) Integer projectID) {

        Payment pmnt = new Payment(title, new BigDecimal(value), Date.valueOf(startDate), DateParser.formatDate(endDate), userID, Cycle.valueOf(cycle.toUpperCase()),
                PaymentType.valueOf(paymentType.toUpperCase()), getProject(projectID));
        paymentRepository.save(pmnt);

        return "Saved";
    }

    @PutMapping(path = "/update")
    public String updatePayment(@RequestParam Integer id, @RequestParam String title, @RequestParam String value, @RequestParam String startDate,
                                @RequestParam String endDate, @RequestParam String cycle, @RequestParam String paymentType, @RequestParam(required = false) Integer projectID) {

        Optional<Payment> optional = paymentRepository.findById(id);
        if (optional.isEmpty()) {
            return "Id does not exist";
        }

        Payment payment = optional.get();

        payment.setTitle(title);
        payment.setValue(new BigDecimal(value));
        payment.setStartDate(Date.valueOf(startDate));
        payment.setEndDate(DateParser.formatDate(endDate));
        payment.setCycle(Cycle.valueOf(cycle.toUpperCase()));
        payment.setPaymentType(PaymentType.valueOf(paymentType.toUpperCase()));
        payment.setProject(getProject(projectID));

        paymentRepository.save(payment);

        return "Saved";
    }

    @GetMapping(path = "/all")
    public Iterable<Payment> getAllUserPayments(@RequestParam String userID) {
        return paymentRepository.findByUserID(userID);
    }

    @GetMapping(path = "/project")
    public Iterable<Payment> getUserProjectPayments(@RequestParam Integer projectID) {
        Project project = getProject(projectID);
        if (project == null) {
            return null;
        }

        return paymentRepository.findByProject(project);
    }

    @GetMapping(path = "/one")
    public Optional<Payment> getOneUserPayment(@RequestParam Integer id) {
        return paymentRepository.findById(id);
    }

    @DeleteMapping(path = "/delete")
    public String deleteUserPayment(@RequestParam Integer id) {
        try {
            paymentRepository.deleteById(id);
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
