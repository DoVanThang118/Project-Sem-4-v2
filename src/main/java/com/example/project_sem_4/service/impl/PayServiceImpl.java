package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.model.dto.OrderDTO;
import com.example.project_sem_4.model.req.OrderReq;
import com.example.project_sem_4.model.res.PayRes;
import com.example.project_sem_4.repository.CartRepository;
import com.example.project_sem_4.service.PayService;
import com.example.project_sem_4.service.PaypalService;
import com.example.project_sem_4.service.VNPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private PaypalService paypalService;


    @Override
    public PayRes makePayment(OrderDTO orderDTO) {

        if ("vnpay".equals(orderDTO.getPaymentMethod()) ) {
            PayRes res = new PayRes();
            res.setMessageCode("200");
            res.setUrl(vnPayService.createOrder(orderDTO));
            res.setMessage("Request successfully fulfilled");
            return res;

        } else if ("paypal".equals(orderDTO.getPaymentMethod())) {
            PayRes res = new PayRes();
            res.setMessageCode("200");
            res.setMessage("Request successfully fulfilled");
            res.setUrl(paypalService.getLinksPayment(orderDTO));
            return res;
        } else {
            PayRes res = new PayRes();
            res.setMessageCode("400");
            res.setMessage("Invalid Payment Method");
            return res;
        }
    }
}
