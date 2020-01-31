package rs.ac.uns.ftn.sep.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.sep.bank.bom.Payment;
import rs.ac.uns.ftn.sep.bank.service.payment.PaymentService;
import rs.ac.uns.ftn.sep.commons.helper.ResponseEntityHelper;

@RequestMapping("/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<?> paymentCompletedCallback(@RequestParam String token) {
        Payment payment = paymentService.completePayment(token);
        return ResponseEntityHelper.sendRedirect(payment.getRedirectUrl());
    }

}
