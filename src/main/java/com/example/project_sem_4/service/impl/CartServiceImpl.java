package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.entity.Product;
import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.CartDTO;
import com.example.project_sem_4.model.mapper.CartMapper;
import com.example.project_sem_4.model.mapper.ProductMapper;
import com.example.project_sem_4.model.req.CartReq;
import com.example.project_sem_4.repository.CartRepository;
import com.example.project_sem_4.repository.ProductRepository;
import com.example.project_sem_4.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public CartDTO saveCart(CartReq req, User user) {
        if (req == null) {
            throw new RuntimeException("NullPointerException");
        }
        if (req.getProductId() != null) {
            Optional<Product> product = productRepository.findById(req.getProductId());
            if (product.isPresent()) {
                req.setProduct(product.get());
            } else {
                throw new RuntimeException("Not found product");
            }
        }
        if (req.getStatus() == null) {
            req.setStatus(1);
        }
        Cart cart = CartMapper.INSTANCE.mapReqToEntity(req);
        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);
        for (Cart c : cartList) {
            c.setUser(user);
        }
        cartRepository.save(cart);
        return CartMapper.INSTANCE.mapEntityToDTO(cart);
    }

    @Override
    public Page<CartDTO> findCarts(Pageable pageable, Long id, Integer status, Long userId) {
        Page<Cart> carts = cartRepository.findCarts(pageable, id, status, userId);
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
    public void deleteAllCart(Long userId) {
        try {
            cartRepository.deleteAllByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Page<CartDTO> getCarts(Pageable pageable, Long userId) {
        Page<Cart> carts = cartRepository.getCarts(pageable, userId);
        return carts.map(CartMapper.INSTANCE::mapEntityToDTO);
    }
}
