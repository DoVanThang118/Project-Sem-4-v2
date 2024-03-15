package com.example.project_sem_4.service;

import com.example.project_sem_4.model.dto.CartItemDTO;
import com.example.project_sem_4.model.req.CartItemReq;
import org.springframework.stereotype.Service;

@Service
public interface CartItemService {

    public CartItemDTO updateCartItem(CartItemReq req);

    void deleteCartItem(Long id);
}
