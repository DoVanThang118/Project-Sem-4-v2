package com.example.project_sem_4.controller;

import com.example.project_sem_4.entity.Order;
import com.example.project_sem_4.service.PaypalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paypal")
public class PaypalController {

    @Autowired
    private PaypalService paypalService;

    @PostMapping("/pay")
    public Payment makePayment(@RequestBody Order order) {
        try {
            return paypalService.createPayment(order.getTotalMoney(), order.getName(), "http://localhost:8080/pay/cancel",
                    "http://localhost:8080/pay/success");
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/pay/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "Payment successful";
            }
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e.getMessage());
        }
        return "Payment failed";
    }

    @GetMapping("/pay/cancel")
    public String cancelPay() {
        return "Payment canceled";
    }
}
