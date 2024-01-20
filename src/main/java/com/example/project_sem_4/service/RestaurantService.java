package com.example.project_sem_4.service;

import com.example.project_sem_4.model.dto.RestaurantDTO;
import com.example.project_sem_4.model.req.RestaurantReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface RestaurantService {

    RestaurantDTO createRestaurant(RestaurantReq req);

    Page<RestaurantDTO> getRestaurant(Pageable pageable, String name, String description, String tel, String address, Integer status);

    void deleteRestaurant(Long id);
}
