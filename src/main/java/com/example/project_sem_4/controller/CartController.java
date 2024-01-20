package com.example.project_sem_4.controller;

import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.CartDTO;
import com.example.project_sem_4.model.req.CartReq;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.service.CartService;
import com.example.project_sem_4.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@Tag(name = "Cart", description = "Cart management APIs")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createCart(@RequestBody CartReq req, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        CartDTO create = cartService.saveCart(req, user);
        return ResponseEntity.ok(create);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateCart(@RequestBody CartReq req, @PathVariable Long id, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        req.setId(id);
        CartDTO update = cartService.saveCart(req, user);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.ok("Delete Success");
    }

    @PostMapping("/list")
    public ResponseEntity<?> getCart(@RequestBody CartReq req) {
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<CartDTO> page = cartService.getCart(
                pageable,
                req.getStatus()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }
}
