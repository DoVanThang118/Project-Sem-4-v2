package com.example.project_sem_4.model.dto;

import lombok.*;

@Getter
@Setter
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
