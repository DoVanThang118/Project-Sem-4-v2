package com.example.project_sem_4.model.dto;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    private Date createDate;

    private Double totalMoney;

    private Integer status;

    private String note;

    private String address;

    private String phone;

    private Set<User> users = new HashSet<>();

    private List<Cart> carts = new ArrayList<>();
}
