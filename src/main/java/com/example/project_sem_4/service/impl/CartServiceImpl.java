package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.*;
import com.example.project_sem_4.model.dto.CartDTO;
import com.example.project_sem_4.model.mapper.CartItemMapper;
import com.example.project_sem_4.model.mapper.CartMapper;
import com.example.project_sem_4.model.mapper.ProductMapper;
import com.example.project_sem_4.model.req.CartItemReq;
import com.example.project_sem_4.model.req.CartReq;
import com.example.project_sem_4.repository.CartItemRepository;
import com.example.project_sem_4.repository.CartRepository;
import com.example.project_sem_4.repository.ProductRepository;
import com.example.project_sem_4.repository.RestaurantRepository;
import com.example.project_sem_4.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public CartDTO saveCart(CartReq req, User user) {
        if (req == null || req.getProductId() == null || req.getRestaurantId() == null || req.getQty() == null) {
            throw new RuntimeException("Invalid cart request");
        }

//        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(req.getRestaurantId());
//        Restaurant restaurant = restaurantOptional.orElseThrow(() -> new RuntimeException("Restaurant not found"));
//
//        Optional<Product> productOptional = productRepository.findById(req.getProductId());
//        Product product = productOptional.orElseThrow(() -> new RuntimeException("Product not found"));
        Restaurant restaurant = restaurantRepository.findById(req.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!Objects.equals(product.getRestaurant().getId(), req.getRestaurantId())) {
            throw new RuntimeException("Product does not belong to the specified restaurant");
        }

        req.setStatus(req.getStatus() == null ? 1 : req.getStatus());

        Cart cart = cartRepository.findByRestaurantId(req.getRestaurantId());
        if (cart == null) {
            cart = new Cart();
            cart.setRestaurant(restaurant);
            cart.setUser(user);
            cart.setStatus(req.getStatus());
            cart.setSubTotal(0.0); // Khởi tạo tổng giá trị cho giỏ hàng mới
        }

        CartItem cartItem = cartItemRepository.findByProductId(req.getProductId());
        if (cartItem != null) {
            // Sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng và tổng giá
            cartItem.setQty(cartItem.getQty() + req.getQty());
            cartItem.getTotal();
        } else {
            // Sản phẩm chưa có trong giỏ hàng, tạo mới một mục giỏ hàng
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setStatus(1);
            cartItem.setProduct(product);
            cartItem.setQty(req.getQty());
            cartItem.setTotal(product.getPrice() * req.getQty());
        }

        // Cập nhật tổng giá trị cho giỏ hàng
        cart.setSubTotal(cart.getSubTotal() + (product.getPrice() * req.getQty()));

        // Lưu hoặc cập nhật thông tin giỏ hàng và mục giỏ hàng
        cartRepository.save(cart);
        cartItemRepository.save(cartItem);

        return CartMapper.INSTANCE.mapEntityToDTO(cart);
    }

    @Override
    public Page<CartDTO> findCarts(Pageable pageable, Long id, Integer status, Long restaurantId, Long userId) {
        Page<Cart> carts = cartRepository.findCarts(pageable, id, status, restaurantId, userId);
        return carts.map(CartMapper.INSTANCE::mapEntityToDTO);
    }

    @Override
    public void deleteCart(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isEmpty()) {
            throw new RuntimeException("Not found cart has id = " + id);
        }
        try {
            cartRepository.delete(cart.get());
        } catch (Exception e) {
            throw new RuntimeException("Database error. Can't delete Cart");
        }
    }

    @Override
    public Page<CartDTO> getCarts(Pageable pageable, Long userId) {
        Page<Cart> carts = cartRepository.getCarts(pageable, userId);
        return carts.map(CartMapper.INSTANCE::mapEntityToDTO);
    }
}
