package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.entity.CartItem;
import com.example.project_sem_4.model.dto.CartItemDTO;
import com.example.project_sem_4.model.mapper.CartItemMapper;
import com.example.project_sem_4.model.req.CartItemReq;
import com.example.project_sem_4.repository.CartItemRepository;
import com.example.project_sem_4.repository.CartRepository;
import com.example.project_sem_4.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;


    @Override
    public CartItemDTO updateCartItem(CartItemReq req) {
        // Tìm kiếm cartItem dựa trên productId
        CartItem cartItem = cartItemRepository.findByProductId(req.getProductId());

        // Kiểm tra xem cartItem có tồn tại không
        if (cartItem != null) {
            // Cập nhật số lượng mới
            cartItem.setQty(req.getQty());

            // Cập nhật lại tổng giá trị dựa trên số lượng mới và giá của sản phẩm
            cartItem.setTotal(cartItem.getProduct().getPrice() * req.getQty());

            // Lưu hoặc cập nhật cartItem
            cartItemRepository.save(cartItem);
        } else {
            throw new RuntimeException("CartItem not found for productId: " + req.getProductId());
        }
        return CartItemMapper.INSTANCE.mapEntityToDTO(cartItem);
    }

    @Override
    public void deleteCartItem(Long id) {
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        if (cartItem.isEmpty()) {
            throw new RuntimeException("Cart item not found with id: " + id);
        }

        CartItem item = cartItem.get();
        Cart cart = item.getCart();

        try {
            // Xóa cartItem khỏi cơ sở dữ liệu
            cartItemRepository.delete(item);

            // Cập nhật subTotal của cart
            double subTotal = cart.getCartItems().stream()
                    .mapToDouble(CartItem::getTotal)
                    .sum();
            cart.setSubTotal(subTotal);
            cartRepository.save(cart);

            // Kiểm tra nếu không còn cartItem nào trong cart, thì xóa luôn cart
//            if (cart.getSubTotal() == 0) {
//                cartRepository.delete(cart);
//            }
            if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
                cartRepository.delete(cart);
            }

        } catch (Exception e) {
            throw new RuntimeException("Database error. Can't delete cart item");
        }
    }
}
