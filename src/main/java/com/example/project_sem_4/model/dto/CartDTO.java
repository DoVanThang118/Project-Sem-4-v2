package com.example.project_sem_4.model.dto;

import com.example.project_sem_4.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Long id;

    private Integer qty;

    private Integer status;

    private Product product;
}
