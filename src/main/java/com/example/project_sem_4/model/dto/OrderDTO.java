package com.example.project_sem_4.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    private String name;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createDate;

    private Double totalMoney;

    private Integer status;

    private String note;

    private String address;

    private String phone;

//    private String paymentMethod;

    private RestaurantDTO restaurant;

    private Set<UserDTO> users;

    private List<OrderDetailDTO> orderDetails;
}
