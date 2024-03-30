package com.example.project_sem_4.controller.admin;

import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.OrderDTO;
import com.example.project_sem_4.model.req.OrderReq;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.service.OrderService;
import com.example.project_sem_4.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/orders")
@Tag(name = "Admin Order", description = "Admin Order management APIs")
public class AdminOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> updateOrder(@RequestBody OrderReq req, @PathVariable Long id, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        OrderDTO update = orderService.updateOrder(req, id, user);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Delete Success");
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> getOrders(@RequestBody OrderReq req, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Long restaurantId = null;
        if (user.getRestaurant() != null) {
            restaurantId = user.getRestaurant().getId();
        }
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<OrderDTO> page = orderService.getOrders(
                pageable,
                req.getId(),
                req.getUserId(),
                restaurantId,
                req.getStatus()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> getAllOrders(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Pageable pageable = PageRequest.of(0,20);
        Page<OrderDTO> orders = orderService.getAllOrders(pageable, user.getId());
        DataRes res = new DataRes();
        res.setData(orders.getContent());
        res.setPagination(new Pagination(orders.getPageable().getPageNumber(), orders.getSize(), orders.getTotalElements()));
        return ResponseEntity.ok(res);
    }
}
