package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.entity.OrderDetail;
import com.example.project_sem_4.entity.Restaurant;
import com.example.project_sem_4.entity.User;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderReq {

    private Long id;

    private String name;

    private String email;

    private LocalDateTime createDate;

    private Double totalMoney;

    private Integer status;

    private String note;

    private String address;

    private String phone;

//    @Pattern(regexp = "^(vnpay|paypal)$", message = "Invalid payment method. Allowed values are 'VNPay' or 'Paypal'")
//    private String paymentMethod;


    private Set<User> users;
    private Long userId;
    private Long shipperId;

    //
    private Restaurant restaurant;
    private Long restaurantId;

    //
    private List<Long> orderDetailId;
    private List<OrderDetail> orderDetails = new ArrayList<>();
    //
    private Long cartId;

    private int pageNumber = 0;

    private int pageSize = 20;
}
