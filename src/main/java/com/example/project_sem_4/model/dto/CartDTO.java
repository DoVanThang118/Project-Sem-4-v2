package com.example.project_sem_4.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {

    private Long id;

    private Integer subTotal;

    private Integer status;

    private RestaurantDTO restaurant;

    private List<CartItemDTO> cartItems;

    private UserDTO user;
}
