package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.*;
import com.example.project_sem_4.model.dto.OrderDTO;
import com.example.project_sem_4.model.mapper.OrderMapper;
import com.example.project_sem_4.model.req.OrderReq;
import com.example.project_sem_4.repository.CartRepository;
import com.example.project_sem_4.repository.OrderDetailRepository;
import com.example.project_sem_4.repository.OrderRepository;
import com.example.project_sem_4.repository.UserRepository;
import com.example.project_sem_4.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDTO saveOrder(OrderReq req, User user) {
        if (req == null || req.getCartId() == null || req.getAddress() == null || req.getPhone() == null) {
            throw new RuntimeException("Invalid order request");
        }

        req.setStatus(req.getStatus() == null ? 1 : req.getStatus());

        Cart cart = cartRepository.findById(req.getCartId()).orElseThrow(() -> new RuntimeException("Cart not found"));

        Order order = OrderMapper.INSTANCE.mapReqToEntity(req);
        LocalDateTime ts = LocalDateTime.now();
        order.setCreateDate(ts);
        order.setTotalMoney(cart.getSubTotal());
        order.getUsers().add(user);
        order.setRestaurant(cart.getRestaurant());
        orderRepository.save(order);

        List<OrderDetail> list = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setStatus(1);
            orderDetail.setQty(cartItem.getQty());
            orderDetail.setPrice(cartItem.getProduct().getPrice());
            orderDetail.setTotal(cartItem.getTotal());
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setOrder(order);
            orderDetailRepository.save(orderDetail);
            list.add(orderDetail);
        }

        order.setOrderDetails(list);

        cartRepository.delete(cart);

        return OrderMapper.INSTANCE.mapEntityToDTO(order);

    }

    @Override
    public OrderDTO updateOrder(OrderReq req, Long id, User user) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        if (req.getName() != null) {
            order.setName(req.getName());
        }
        if (req.getEmail() != null) {
            order.setEmail(req.getEmail());
        }
        if (req.getId() != null) {
            order.setId(req.getId());
        }
        if (req.getAddress() != null) {
            order.setAddress(req.getAddress());
        }
        if (req.getNote() != null) {
            order.setNote(req.getNote());
        }
        if (req.getPhone() !=null) {
            order.setPhone(req.getPhone());
        }
        if (req.getStatus() != null) {
            order.setStatus(req.getStatus());
        }
        User shipper = userRepository.findById(req.getShipperId()).orElseThrow(() -> new RuntimeException("Shipper not found"));
        order.getUsers().add(shipper);

        order.getUsers().add(user);
        orderRepository.save(order);

        return OrderMapper.INSTANCE.mapEntityToDTO(order);
    }

    @Override
    public Page<OrderDTO> getOrders(Pageable pageable, Long id, Long userId, Long restaurantId, Integer status) {
        Page<Order> orders = orderRepository.findOrders(pageable, id, userId, restaurantId, status);
        return orders.map(OrderMapper.INSTANCE::mapEntityToDTO);
    }

    @Override
    public void deleteOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new RuntimeException("Not found order has id = " + id);
        }
        try {
            orderRepository.delete(order.get());
        } catch (Exception e) {
            throw new RuntimeException("Database error. Can't delete order");
        }
    }

    @Override
    public Page<OrderDTO> getAllOrders(Pageable pageable, Long userId) {
        Page<Order> orders = orderRepository.findAllByUserId(pageable, userId);
        return orders.map(OrderMapper.INSTANCE::mapEntityToDTO);
    }
}
