package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.CartItem;
import com.example.project_sem_4.entity.Product;
import com.example.project_sem_4.entity.Restaurant;
import com.example.project_sem_4.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartReq {

    private Long id;

    private Integer qty;

    private Double subTotal;

    private Integer status;

    //
    private Long productId;
    private Product product;
    //

    //
    private List<CartItem> cartItems;
    //

    //
    private Long restaurantId;
    private Restaurant restaurant;
    //

    private Long userId;
    private User user;

    private int pageNumber = 0;

    private int pageSize = 20;
}
