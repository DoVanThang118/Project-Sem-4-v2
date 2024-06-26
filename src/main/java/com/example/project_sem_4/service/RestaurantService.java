package com.example.project_sem_4.service;

import com.example.project_sem_4.model.dto.FinanceDTO;
import com.example.project_sem_4.model.dto.RestaurantDTO;
import com.example.project_sem_4.model.req.BrandReq;
import com.example.project_sem_4.model.req.RestaurantReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

@Service
public interface RestaurantService {

    RestaurantDTO createRestaurant(RestaurantReq req) throws IOException;

    RestaurantDTO updateRestaurant(RestaurantReq req, Long id) throws IOException;

    RestaurantDTO updateAvatar(RestaurantReq req, Long id) throws IOException;

    Page<RestaurantDTO> getRestaurant(Pageable pageable, Long id, String name, String description, String tel, String address, List<String> meals, List<String> cuisines, LocalTime hourStart, LocalTime hourEnd, Double rate, Integer status);

    void deleteRestaurant(Long id);

    Page<RestaurantDTO> getAllRestaurants(Pageable pageable);

    FinanceDTO totalRevenue(Long id, Integer status);
}
