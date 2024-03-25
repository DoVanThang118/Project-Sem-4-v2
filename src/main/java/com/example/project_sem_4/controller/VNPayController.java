package com.example.project_sem_4.controller;

import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.req.OrderReq;
import com.example.project_sem_4.model.res.PayRes;
import com.example.project_sem_4.service.OrderService;
import com.example.project_sem_4.service.PayService;
import com.example.project_sem_4.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/vnpay")
public class VNPayController {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
//
//    @PostMapping("/submitOrder")
//    public String submidOrder(@RequestParam("amount") int orderTotal, HttpServletRequest request)
//    {
//        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        return vnPayService.createOrder(orderTotal, baseUrl,request);
//    }
//
//    @GetMapping("/vnpay-payment")
//    public ResponseEntity<?> GetMapping(HttpServletRequest request){
//        int paymentStatus =vnPayService.orderReturn(request);
//
//        String orderInfo = request.getParameter("vnp_OrderInfo");
//        String paymentTime = request.getParameter("vnp_PayDate");
//        String transactionId = request.getParameter("vnp_TransactionNo");
//        String totalPrice = request.getParameter("vnp_Amount");
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("paymentStatus", paymentStatus);
//        response.put("orderInfo", orderInfo);
//        response.put("paymentTime", paymentTime);
//        response.put("transactionId", transactionId);
//        response.put("totalPrice", totalPrice);
//
//
//        return paymentStatus == 1 ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body("order-fail");
//    }
}
