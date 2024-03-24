package com.example.project_sem_4.service;

import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.dto.OrderDTO;
import com.example.project_sem_4.model.req.OrderReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    OrderDTO saveOrder (OrderReq req, User user);

    Page<OrderDTO> getOrders(Pageable pageable, Long id, Long userId, Integer status);

    void deleteOrder(Long id);

    Page<OrderDTO> getAllOrders(Pageable pageable, Long user);

}
