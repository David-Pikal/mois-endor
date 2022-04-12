package cz.uhk.mois.endor.bankapi;

import cz.uhk.mois.endor.bankapi.model.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="root-service", url = "https://moisbanking.herokuapp.com/v1/payment")
public interface BankApiFeign {

    @GetMapping("/findByDate")
    List<Payment> getPayments(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam int accountId);

    // TODO - future work
    @PostMapping("/create")
    Payment createPayment();
}
