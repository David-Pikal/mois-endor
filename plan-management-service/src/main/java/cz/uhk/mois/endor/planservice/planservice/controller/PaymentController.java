package cz.uhk.mois.endor.planservice.planservice.controller;

import cz.uhk.mois.endor.planservice.planservice.model.Project;
import cz.uhk.mois.endor.planservice.planservice.repository.PaymentRepository;
import cz.uhk.mois.endor.planservice.planservice.model.Payment;
import cz.uhk.mois.endor.planservice.planservice.repository.ProjectRepository;
import cz.uhk.mois.endor.planservice.planservice.util.Cycle;
import cz.uhk.mois.endor.planservice.planservice.util.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String addNewPayment(@RequestParam String title, @RequestParam String value, @RequestParam String startDate, @RequestParam Integer userID,
                               @RequestParam String endDate, @RequestParam String cycle, @RequestParam String paymentType, @RequestParam(required = false) Integer projectID) {

        Date endD = null;
        if (!endDate.equals("")) {
            endD = Date.valueOf(endDate);
        }

        Project project = null;
        if (projectID != null && projectID > 0) {
            Optional<Project> optional = projectRepository.findById(projectID);
            if (optional.isPresent()) {
                project = optional.get();
            }
        }

        Payment pmnt = new Payment(title, new BigDecimal(value), Date.valueOf(startDate), endD, userID, Cycle.valueOf(cycle.toUpperCase()),
                PaymentType.valueOf(paymentType.toUpperCase()), project);
        paymentRepository.save(pmnt);

        return "Saved";
    }

    @GetMapping(path = "/all")
    public Iterable<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

}
