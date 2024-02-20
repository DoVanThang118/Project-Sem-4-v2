package com.example.project_sem_4.service;

import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.dto.OrderDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {

    List<OrderDetailDTO> create(User user);

    Page<OrderDetailDTO> getOrderDetails(Pageable pageable, Long id, Integer status);

    public void deleteOrderDetail(Long id);

    Page<OrderDetailDTO> getAllOrderDetails(Pageable pageable);

}
