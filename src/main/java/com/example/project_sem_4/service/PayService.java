package com.example.project_sem_4.service;

import com.example.project_sem_4.model.dto.OrderDTO;
import com.example.project_sem_4.model.req.OrderReq;
import com.example.project_sem_4.model.res.PayRes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PayService {
    PayRes makePayment(OrderDTO orderDTO);
}
