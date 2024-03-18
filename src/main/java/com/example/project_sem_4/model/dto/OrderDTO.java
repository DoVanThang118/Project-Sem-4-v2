package com.example.project_sem_4.model.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    private String name;

    private String email;

    private Timestamp createDate;

    private Double totalMoney;

    private Integer status;

    private String note;

    private String address;

    private String phone;

    private RestaurantDTO restaurant;

    private Set<UserDTO> users;

    private List<OrderDetailDTO> orderDetails;
}
