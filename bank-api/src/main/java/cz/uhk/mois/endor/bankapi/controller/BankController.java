package cz.uhk.mois.endor.bankapi.controller;

import cz.uhk.mois.endor.bankapi.service.BankApiFeign;
import cz.uhk.mois.endor.bankapi.model.payment.Payment;
import cz.uhk.mois.endor.bankapi.model.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bank")
@AllArgsConstructor
public class BankController {

    private BankApiFeign proxy;

    @GetMapping("/payment")
    public List<Payment> paymentList(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam int accountId) {
        log.info(String.format("Get payments from %s, to %s.", dateFrom, dateTo));
        return proxy.getPayments(dateFrom, dateTo, accountId);
    }

    @GetMapping("/transaction")
    public List<Transaction> transactionList(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam int accountId) {
        log.info(String.format("Get transactions from %s, to %s.", dateFrom, dateTo));
        return proxy.getTransactions(dateFrom, dateTo, accountId);
    }

}
