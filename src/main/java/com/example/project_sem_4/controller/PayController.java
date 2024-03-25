package com.example.project_sem_4.controller;

import com.example.project_sem_4.model.dto.OrderDTO;
import com.example.project_sem_4.model.res.PayRes;
import com.example.project_sem_4.service.OrderService;
import com.example.project_sem_4.service.PayService;
import com.example.project_sem_4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pay")
public class PayController {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<?> pay(@RequestBody OrderDTO orderDTO) {
        PayRes payRes = payService.makePayment(orderDTO);
        return ResponseEntity.ok(payRes);
    }
}
