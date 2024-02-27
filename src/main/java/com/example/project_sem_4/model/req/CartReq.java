package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.Product;
import com.example.project_sem_4.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartReq {

    private Long id;

    private Integer qty;

    private Integer status;

    //
    private Long productId;
    private Product product;
    //
    private Long userId;
    private User user;

    private int pageNumber = 0;

    private int pageSize = 20;
}
