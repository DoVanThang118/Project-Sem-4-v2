package com.example.project_sem_4.controller.admin;

import com.example.project_sem_4.model.dto.RestaurantDTO;
import com.example.project_sem_4.model.req.RestaurantReq;
import com.example.project_sem_4.service.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/restaurants")
@Tag(name = "Admin Restaurant", description = "Restaurant Admin management APIs")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveRestaurant(@ModelAttribute RestaurantReq req) throws IOException {
        RestaurantDTO create = restaurantService.createRestaurant(req);
        return ResponseEntity.ok(create);
    }

    @PutMapping("/{id}")
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

    @GetMapping("total_revenue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTotalRevenue() {
        Double total = restaurantService.totalRevenue();
        return ResponseEntity.ok(total);
    }
}
