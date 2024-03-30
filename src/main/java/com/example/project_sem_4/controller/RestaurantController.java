package com.example.project_sem_4.controller;

import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.dto.RestaurantDTO;
import com.example.project_sem_4.model.req.BrandReq;
import com.example.project_sem_4.model.req.RestaurantReq;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.service.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/restaurants")
@Tag(name = "Restaurant", description = "Restaurant management APIs")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/list")
    public ResponseEntity<?> getRestaurants(@RequestBody RestaurantReq req) {
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<RestaurantDTO> page = restaurantService.getRestaurant(
                pageable,
                req.getId(),
                req.getName(),
                req.getDescription(),
                req.getTel(),
                req.getAddress(),
                req.getMeals(),
                req.getCuisines(),
                req.getHourStart(),
                req.getHourEnd(),
                req.getRate(),
                req.getStatus()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllRestaurants() {
        Pageable pageable = PageRequest.of(0,20);
        Page<RestaurantDTO> restaurants = restaurantService.getAllRestaurants(pageable);
            DataRes res = new DataRes();
        res.setData(restaurants.getContent());
        res.setPagination(new Pagination(restaurants.getPageable().getPageNumber(), restaurants.getSize(), restaurants.getTotalElements()));
        return ResponseEntity.ok(res);
    }
}
