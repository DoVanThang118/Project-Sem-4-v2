package com.example.project_sem_4.controller;

import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.CartDTO;
import com.example.project_sem_4.model.dto.CartItemDTO;
import com.example.project_sem_4.model.req.CartItemReq;
import com.example.project_sem_4.model.req.CartReq;
import com.example.project_sem_4.service.CartItemService;
import com.example.project_sem_4.service.CartService;
import com.example.project_sem_4.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
@Tag(name = "Cart Item", description = "Cart Item management APIs")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCart(@RequestBody CartItemReq req, @PathVariable Long id, Authentication authentication) {
        req.setId(id);
        CartItemDTO update = cartItemService.updateCartItem(req);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok("Delete Success");
    }
}
