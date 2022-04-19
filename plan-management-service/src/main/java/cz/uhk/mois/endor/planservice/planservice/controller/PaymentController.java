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
@RequestMapping(path="/payment")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping(path = "/add")
    public String addNewPayment(@RequestParam String title, @RequestParam String value, @RequestParam String startDate, @RequestParam String endDate,
                                @RequestParam Integer userID, @RequestParam String cycle, @RequestParam String paymentType, @RequestParam(required = false) Integer projectID) {

        Payment pmnt = new Payment(title, new BigDecimal(value), Date.valueOf(startDate), DateParser.formatDate(endDate), userID, Cycle.valueOf(cycle.toUpperCase()),
                PaymentType.valueOf(paymentType.toUpperCase()), getProject(projectID));
        paymentRepository.save(pmnt);

        return "Saved";
    }

    @PutMapping(path = "/update")
    public String updatePayment(@RequestParam Integer id, @RequestParam String title, @RequestParam String value, @RequestParam String startDate,
                                @RequestParam String endDate, @RequestParam String cycle, @RequestParam String paymentType, @RequestParam(required = false) Integer projectID) {

        Optional<Payment> optionalP = paymentRepository.findById(id);
        if (optionalP.isEmpty()) {
            return "id does not exist";
        }

        Payment payment = optionalP.get();

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
    public Iterable<Payment> getAllUserPayments(@RequestParam Integer userID) {
        return paymentRepository.findByUserID(userID);
    }

    @GetMapping(path = "/project")
    public Iterable<Payment> getUserProjectPayments(@RequestParam Integer projectID) {
        Project project;
        if (projectID == null || projectID <= 0) {
            return null;
        }
        Optional<Project> optional = projectRepository.findById(projectID);
        if (optional.isEmpty()) {
           return null;
        }
        project = optional.get();

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
            return "deleted";
        } catch (EmptyResultDataAccessException e) {
            return "id does not exist";
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
