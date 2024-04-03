package com.example.project_sem_4.controller.admin;

import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.FinanceDTO;
import com.example.project_sem_4.model.dto.RestaurantDTO;
import com.example.project_sem_4.model.req.RestaurantReq;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.service.RestaurantService;
import com.example.project_sem_4.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/restaurants")
@Tag(name = "Admin Restaurant", description = "Restaurant Admin management APIs")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveRestaurant(@ModelAttribute RestaurantReq req) throws IOException {
        RestaurantDTO create = restaurantService.createRestaurant(req);
        return ResponseEntity.ok(create);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRestaurant(@RequestBody RestaurantReq req, @PathVariable Long id) throws IOException {
        RestaurantDTO update = restaurantService.updateRestaurant(req, id);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @PutMapping("/avatar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAvatar(@ModelAttribute RestaurantReq req, @PathVariable Long id) throws IOException {
        RestaurantDTO result = restaurantService.updateAvatar(req, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok("Delete Success");
    }

    @GetMapping("total_revenue")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> getTotalRevenue(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Long restaurantId = null;
        Integer restaurantStatus = 1;
        if (user.getRestaurant() != null) {
            restaurantId = user.getRestaurant().getId();
            restaurantStatus = user.getRestaurant().getStatus();
        }
        FinanceDTO financeDTO = restaurantService.totalRevenue(restaurantId, restaurantStatus);
        return ResponseEntity.ok(financeDTO);
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> getAllRestaurants() {
        Pageable pageable = PageRequest.of(0,20);
        Page<RestaurantDTO> restaurants = restaurantService.getAllRestaurants(pageable);
        DataRes res = new DataRes();
        res.setData(restaurants.getContent());
        res.setPagination(new Pagination(restaurants.getPageable().getPageNumber(), restaurants.getSize(), restaurants.getTotalElements()));
        return ResponseEntity.ok(res);
    }
}
