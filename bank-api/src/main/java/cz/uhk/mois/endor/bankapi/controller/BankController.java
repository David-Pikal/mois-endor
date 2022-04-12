package cz.uhk.mois.endor.bankapi.controller;

import cz.uhk.mois.endor.bankapi.BankApiFeign;
import cz.uhk.mois.endor.bankapi.model.Payment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bank")
@AllArgsConstructor
public class BankController {

    private BankApiFeign proxy;

    @GetMapping("/payment")
    public List<Payment> paymentList(@RequestParam String dateFrom, @RequestParam String dateTo, @RequestParam int accountId) {
        log.info(String.format("Payment from %s, to %s.", dateFrom, dateTo));

        //        Mono<Object> result = webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .path("/payment/findByDate")
//                        .queryParam("dateFrom", "2012-04-23T18%3A25%3A43.511Z")
//                        .queryParam("dateTo", "2020-04-23T18%3A25%3A43.511Z")
//                        .queryParam("accountId", 123)
//                        .build())
//                .retrieve()
//                .bodyToMono(Object.class).flatMap(body -> {
//                   log.debug("Body is: ", body);
//                   return Mono.just(body);
//                });

        // TODO accountId remove in the future
        return proxy.getPayments(dateFrom, dateTo, accountId);
    }

    @PostMapping("/payment")
    public Payment createPayment(@RequestBody @Valid Payment payment) {
        log.info("Trying to save this payment: " + payment);

        // TODO - save to
        return payment;
    }

    // Get JWT
    @GetMapping("/cars/principal/token")
    public String getPrincipal(Principal principal, Authentication authentication, @AuthenticationPrincipal Jwt jwt) {
        log.info(principal.getName());
        log.info(jwt.getTokenValue());
        log.info(((Jwt)authentication.getCredentials()).getTokenValue());
        return ((Jwt)authentication.getCredentials()).getTokenValue();
    }
}
