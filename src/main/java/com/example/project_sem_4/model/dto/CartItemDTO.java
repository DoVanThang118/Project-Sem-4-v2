package com.example.project_sem_4.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    private Long id;

    private Integer status;

    private Integer qty;

    private Double total;

    private ProductDTO product;
}
