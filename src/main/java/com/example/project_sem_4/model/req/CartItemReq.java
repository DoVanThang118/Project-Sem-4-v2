package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.Product;
import com.example.project_sem_4.model.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemReq {

    private Long id;

    private Integer status;

    private Integer qty;

    private Double total;

    private Long productId;
    private Product product;

    private int pageNumber = 0;

    private int pageSize = 20;
}
