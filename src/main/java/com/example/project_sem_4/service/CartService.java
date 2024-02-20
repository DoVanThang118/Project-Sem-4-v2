package com.example.project_sem_4.service;

import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.dto.CartDTO;
import com.example.project_sem_4.model.req.CartReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    CartDTO saveCart (CartReq req, User user);

    Page<CartDTO> getCart(Pageable pageable, Long id, Integer status);

    void deleteCart(Long id);

    Page<CartDTO> getAllCarts(Pageable pageable);
}
