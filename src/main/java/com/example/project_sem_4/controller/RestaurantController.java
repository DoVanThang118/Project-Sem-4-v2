package com.example.project_sem_4.controller;

import com.example.project_sem_4.model.dto.RestaurantDTO;
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

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveRestaurant(@ModelAttribute RestaurantReq req) throws IOException {
        RestaurantDTO create = restaurantService.createRestaurant(req);
        return ResponseEntity.ok(create);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRestaurant(@ModelAttribute RestaurantReq req, @PathVariable Long id) throws IOException {
        req.setId(id);
        RestaurantDTO update = restaurantService.createRestaurant(req);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok("Delete Success");
    }

    @PostMapping("/list")
    public ResponseEntity<?> getRestaurants(@ModelAttribute RestaurantReq req) {
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<RestaurantDTO> page = restaurantService.getRestaurant(
                pageable,
                req.getId(),
                req.getName(),
                req.getDescription(),
                req.getTel(),
                req.getAddress(),
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
