package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailReq {

    private Long id;

    private Integer quantity;

    private Double price;

    private Double sum;

    private Integer status;

    //
    private Order order;
    private Long orderId;
    //

    //
    private Cart cart;
    private Long cartId;
    //

    private int pageNumber = 0;

    private int pageSize = 20;
}
