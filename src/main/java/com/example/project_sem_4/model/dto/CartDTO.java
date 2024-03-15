package com.example.project_sem_4.model.dto;

import com.example.project_sem_4.entity.CartItem;
import com.example.project_sem_4.entity.Product;
import com.example.project_sem_4.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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
