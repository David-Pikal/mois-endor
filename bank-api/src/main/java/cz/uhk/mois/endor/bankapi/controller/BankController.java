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

@Slf4j
@RestController
@RequestMapping("/bank")
@AllArgsConstructor
public class BankController {

    private BankApiFeign proxy;

    @GetMapping("/payment")
    public BigDecimal paymentList(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam int accountId) {
        // get all payments between given dates
        var rawPayments = proxy.getPayments(dateFrom, dateTo, accountId);

        log.info(String.format("Get payments from %s, to %s.", dateFrom, dateTo));

        var sum = new BigDecimal(0);

        for (Payment payment : rawPayments) {
            // 2012-04-23T18:25:43.511Z
            // parse Strings - need only date part
            var firstPayment = LocalDate.parse(payment.recuringPayment().firstPayment().substring(0,10));
            var lastPayment = LocalDate.parse(payment.recuringPayment().lastPayment().substring(0,10));

            // enhanced switch - do not need break statement
            long numberOfPayments = 0;
            switch (payment.recuringPayment().interval()) {
                case WEEK -> numberOfPayments = ChronoUnit.WEEKS.between(firstPayment, lastPayment);
                case MONTH -> numberOfPayments = ChronoUnit.MONTHS.between(firstPayment, lastPayment);
                case YEAR -> numberOfPayments = ChronoUnit.YEARS.between(firstPayment, lastPayment);
                default -> log.error("Payment do not have Interval!");
            }
            log.info(String.format("Number of %s between %s and %s is: %s.", payment.recuringPayment().interval(), firstPayment, lastPayment, numberOfPayments));
            //sum = sum.add(new BigDecimal(payment.value().amount().multiply() * (numberOfPayments + 1)));
            sum = sum.subtract(payment.value().amount().multiply(new BigDecimal(numberOfPayments + 1)));
            log.info(String.format("Big decimal: %s", sum));
        }

        return sum;
    }

    @GetMapping("/transaction")
    public BigDecimal transactionList(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam int accountId) {
        log.info(String.format("Get transactions from %s, to %s.", dateFrom, dateTo));
        var sum = new BigDecimal(0);
        var rawTransactions = proxy.getTransactions(dateFrom, dateTo, accountId);

        for (Transaction transaction : rawTransactions) {
            var amount = transaction.value().amount();
            var direction = transaction.direction();
            switch (direction) {
                case INCOMING -> sum = sum.add(amount);
                case OUTCOMING -> sum = sum.subtract(amount);
                default -> log.error("Wrong direction.");
            }
            log.info(String.format("Transaction direction: %s with amount: %s.", direction, amount));
        }

        return sum;
    }

}
