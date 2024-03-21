package com.example.project_sem_4.controller;

import com.example.project_sem_4.entity.Order;
import com.example.project_sem_4.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paypal")
public class PaypalController {

    @Autowired
    private PaypalService paypalService;

    public static final String SUCCESS_URL = "pay";
    public static final String CANCEL_URL = "pay/cancel";

    @PostMapping("/pay")
    public ResponseEntity<String> payment(@RequestBody Order order) {
        try {
            Payment payment = paypalService.createPayment(order.getTotalMoney(), order.getName(), "http://localhost:3000/" + CANCEL_URL,
                    "http://localhost:3000/" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return ResponseEntity.status(HttpStatus.OK).body(link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment creation failed.");
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public ResponseEntity<String> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("payerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return ResponseEntity.ok(payment.getTransactions().get(0).getDescription());
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.badRequest().body("failed");
    }
}
