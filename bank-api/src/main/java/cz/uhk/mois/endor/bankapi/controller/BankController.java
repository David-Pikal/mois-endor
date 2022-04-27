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
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bank")
@AllArgsConstructor
public class BankController {

    private BankApiFeign proxy;

    @GetMapping("/payment/value")
    public BigDecimal paymentValue(@RequestParam String dateFrom, @RequestParam String dateTo) {

        // parse Strings - need only date part
        var dateFromParsed = LocalDate.parse(dateFrom.substring(0, 10));
        var dateToParsed = LocalDate.parse(dateTo.substring(0, 10));

        if(dateToParsed.isBefore(dateFromParsed))  {
            return new BigDecimal(0);
        }

        // get all payments between given dates - there is mistake in BANK-API
        var rawPayments = proxy.getPayments(dateFrom, dateTo, 123)
                .stream().filter(
                        payment -> LocalDate.parse(payment.recuringPayment().firstPayment().substring(0, 10)).isBefore(dateToParsed)
                                &&
                                dateFromParsed.isBefore(
                                        LocalDate.parse(payment.recuringPayment().lastPayment().substring(0, 10))
                                )
                ).toList();

        log.info(String.format("Get payments from %s, to %s.", dateFrom, dateTo));

        var sum = new BigDecimal(0);

        for (Payment payment : rawPayments) {
            var firstPayment = LocalDate.parse(payment.recuringPayment().firstPayment().substring(0,10));
            var lastPayment = LocalDate.parse(payment.recuringPayment().lastPayment().substring(0,10));

            log.info(payment.recuringPayment().toString());

            long numberOfPayments = 0;

            // cut intervals
            if(firstPayment.isBefore(dateFromParsed)) {
               firstPayment = dateFromParsed;
            }

            if(lastPayment.isAfter(dateToParsed)) {
                lastPayment = dateToParsed;
            }

            // enhanced switch - do not need break statement
            switch (payment.recuringPayment().interval()) {
                case WEEK -> numberOfPayments = ChronoUnit.WEEKS.between(firstPayment, lastPayment);
                case MONTH -> numberOfPayments = ChronoUnit.MONTHS.between(firstPayment, lastPayment);
                case YEAR -> numberOfPayments = ChronoUnit.YEARS.between(firstPayment, lastPayment);
                default -> log.error("Payment does not contain Interval!");
            }
            log.info(String.format("Number of %s between %s and %s is: %s.", payment.recuringPayment().interval(), firstPayment, lastPayment, numberOfPayments));

            sum = sum.subtract(payment.value().amount().multiply(new BigDecimal(numberOfPayments + 1)));

            log.info(String.format("Summary of payments is : %s", sum));
        }

        return sum;
    }

    @GetMapping("/transaction/value")
    public BigDecimal transactionsValue(@RequestParam String dateFrom, @RequestParam String dateTo) {
        log.info(String.format("Get transactions from %s, to %s.", dateFrom, dateTo));
        var sum = new BigDecimal(0);
        var rawTransactions = proxy.getTransactions(dateFrom, dateTo, 123);

        for (Transaction transaction : rawTransactions) {
            var amount = transaction.value().amount();
            var direction = transaction.direction();
            switch (direction) {
                case INCOMING -> sum = sum.add(amount);
                case OUTGOING -> sum = sum.subtract(amount);
                default -> log.error("Wrong direction.");
            }
            log.info(String.format("Transaction direction: %s with amount: %s.", direction, amount));
        }

        return sum;
    }

    @GetMapping("/transaction")
    public List<Transaction> transactionList(@RequestParam String dateFrom, @RequestParam String dateTo) {
        return proxy.getTransactions(dateFrom, dateTo, 123);
    }

    @GetMapping("/payment")
    public List<Payment> paymentList(@RequestParam String dateFrom, @RequestParam String dateTo) {
        // parse Strings - need only date part
        var dateFromParsed = LocalDate.parse(dateFrom.substring(0, 10));
        var dateToParsed = LocalDate.parse(dateTo.substring(0, 10));

        if(dateToParsed.isBefore(dateFromParsed))  {
            return Collections.emptyList();
        }

        // get all payments between given dates - there is mistake in BANK-API
        var payments = proxy.getPayments(dateFrom, dateTo, 123)
                .stream().filter(
                        payment -> LocalDate.parse(payment.recuringPayment().firstPayment().substring(0, 10)).isBefore(dateToParsed)
                                &&
                                dateFromParsed.isBefore(
                                        LocalDate.parse(payment.recuringPayment().lastPayment().substring(0, 10))
                                )
                ).toList();

        log.info(String.format("Get payments from %s, to %s.", dateFrom, dateTo));
        return payments;
    }
}
