package com.example.project_sem_4.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    private Long id;

    private Integer status;

    private Integer qty;

    private Double total;

    private ProductDTO product;
}
