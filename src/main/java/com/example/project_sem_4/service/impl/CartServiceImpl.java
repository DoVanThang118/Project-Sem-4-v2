package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.entity.Product;
import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.CartDTO;
import com.example.project_sem_4.model.mapper.CartMapper;
import com.example.project_sem_4.model.req.CartReq;
import com.example.project_sem_4.repository.CartRepository;
import com.example.project_sem_4.repository.ProductRepository;
import com.example.project_sem_4.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
            product.ifPresent(req::setProduct);
        }

        Cart cart = CartMapper.INSTANCE.mapReqToEntity(req);
        cart.setUser(user);
        user.addCart(cart);
        cartRepository.save(cart);
        return CartMapper.INSTANCE.mapEntityToDTO(cart);
    }

    @Override
    public Page<CartDTO> getCart(Pageable pageable, Integer status) {
        Page<Cart> carts = cartRepository.findCarts(pageable, status);
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
}
