package com.example.project_sem_4.model.dto;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    private Long id;

    private Integer quantity;

    private Double price;

    private Double sum;

    private Integer status;

    private Order order;

    private Cart cart;
}
