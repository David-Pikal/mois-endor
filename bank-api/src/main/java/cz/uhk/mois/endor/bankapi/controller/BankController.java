package cz.uhk.mois.endor.bankapi.controller;

import cz.uhk.mois.endor.bankapi.model.payment.Payment;
import cz.uhk.mois.endor.bankapi.model.transaction.Transaction;
import cz.uhk.mois.endor.bankapi.service.BankApiFeign;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bank")
@AllArgsConstructor
public class BankController {

    private BankApiFeign proxy;

    @GetMapping("/payment")
    public List<Payment> paymentList(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam int accountId) {
        // get all payments between given dates
        var rawPayments = proxy.getPayments(dateFrom, dateTo, accountId);

        log.info(String.format("Get payments from %s, to %s.", dateFrom, dateTo));

        var sum = new BigDecimal(0);

        for (Payment payment : rawPayments) {
            // 2012-04-23T18:25:43.511Z
            // parse Strings - need only date part
            var firstPayment = LocalDate.parse(payment.recuringPayment().firstPayment().substring(0,10));
            var lastPayment = LocalDate.parse(payment.recuringPayment().lastPayment().substring(0,10));

            long numberOfPayments = 0;
            switch (payment.recuringPayment().interval()) {
                // enhanced switch - do not need break statement
                case WEEK -> {
                    numberOfPayments = ChronoUnit.WEEKS.between(firstPayment, lastPayment);
                    log.info(String.format("Number of weeks between %s and %s is: %s.", firstPayment, lastPayment, numberOfPayments));
                }
                case MONTH -> {
                    numberOfPayments = ChronoUnit.MONTHS.between(firstPayment, lastPayment);
                    log.info(String.format("Number of months between %s and %s is: %s.", firstPayment, lastPayment, numberOfPayments));
                }
                case YEAR -> {
                    numberOfPayments = ChronoUnit.YEARS.between(firstPayment, lastPayment);
                    log.info(String.format("Number of years between %s and %s is: %s.", firstPayment, lastPayment, numberOfPayments));
                }
                default -> log.error("Payment do not have Interval!");
            }
            sum = sum.add(new BigDecimal(payment.value().amount() * (numberOfPayments + 1)));
            log.info(String.format("Big decimal: %s", sum.toEngineeringString()));
        }

        // TODO - return value
        return proxy.getPayments(dateFrom, dateTo, accountId);
    }

    @GetMapping("/transaction")
    public List<Transaction> transactionList(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam int accountId) {
        log.info(String.format("Get transactions from %s, to %s.", dateFrom, dateTo));
        var sum = new BigDecimal(0);
        var rawTransactions = proxy.getTransactions(dateFrom, dateTo, accountId);

        // TODO sum transactions
        for (Transaction transaction : rawTransactions) {
            log.info(transaction.toString());
        }

        return proxy.getTransactions(dateFrom, dateTo, accountId);
    }

}
