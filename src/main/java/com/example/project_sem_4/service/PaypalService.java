package com.example.project_sem_4.service;

import com.example.project_sem_4.config.paypal.PaypalPaymentIntent;
import com.example.project_sem_4.config.paypal.PaypalPaymentMethod;
import com.example.project_sem_4.entity.Order;
import com.example.project_sem_4.model.dto.OrderDTO;
import com.example.project_sem_4.model.req.OrderReq;
import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;

import org.springframework.stereotype.Service;

@Service
public interface PaypalService {

    public Payment createPayment(
            Double total,
            String currency,
            PaypalPaymentMethod method,
            PaypalPaymentIntent intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException;

    public Payment executePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException;

    public String getLinksPayment (OrderDTO orderDTO);
}
