package com.example.project_sem_4.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;

import org.springframework.stereotype.Service;

@Service
public interface PaypalService {

    public Payment createPayment(
            Double total,
            String description,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException;

    public Payment executePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException;
}
