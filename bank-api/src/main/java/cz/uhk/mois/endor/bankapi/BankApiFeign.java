package cz.uhk.mois.endor.bankapi;

import cz.uhk.mois.endor.bankapi.model.payment.Payment;
import cz.uhk.mois.endor.bankapi.model.transaction.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="bank-api-service", url = "https://moisbanking.herokuapp.com/v1/")
public interface BankApiFeign {

    @GetMapping("/payment/findByDate")
    List<Payment> getPayments(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam int accountId);

    @GetMapping("/transaction/findByDate")
    List<Transaction> getTransactions(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam int accountId);
}
