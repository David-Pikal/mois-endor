package cz.uhk.mois.endor.planservice.planservice.controller;

import cz.uhk.mois.endor.planservice.planservice.repository.PaymentRepository;
import cz.uhk.mois.endor.planservice.planservice.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;

@RestController
@RequestMapping(path="/payment")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping(path = "/add")
    public String addNewIncome(@RequestParam String title, @RequestParam String value,
                                             @RequestParam String date, @RequestParam Integer userID) {

        //Payment n = new Payment(title, new BigDecimal(value), Date.valueOf(date), userID);
        //paymentRepository.save(n);

        return "Saved";
    }

    @GetMapping(path = "/all")
    public Iterable<Payment> getAllIncomes() {
        return paymentRepository.findAll();
    }

}
