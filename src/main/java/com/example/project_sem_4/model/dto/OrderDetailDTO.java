package com.example.project_sem_4.model.dto;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.entity.Order;
import com.example.project_sem_4.entity.Product;
import com.example.project_sem_4.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    private Long id;

    private Integer qty;

    private Double price;

    private Double total;

    private Integer status;

    private ProductDTO product;

//    private CartDTO cart;
}
