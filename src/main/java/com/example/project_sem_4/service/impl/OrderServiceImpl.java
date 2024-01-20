package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.Order;
import com.example.project_sem_4.entity.OrderDetail;
import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.OrderDTO;
import com.example.project_sem_4.model.mapper.OrderMapper;
import com.example.project_sem_4.model.req.OrderReq;
import com.example.project_sem_4.repository.OrderDetailRepository;
import com.example.project_sem_4.repository.OrderRepository;
import com.example.project_sem_4.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDTO saveOrder(OrderReq req, User user) {
        if (req == null) {
            throw new RuntimeException("NullPointerException");
        }
        if (req.getOrderDetailId() != null) {
            for (Long oid : req.getOrderDetailId()) {
                Optional<OrderDetail> orderDetail = orderDetailRepository.findById(oid);
                orderDetail.ifPresent(detail -> req.getOrderDetails().add(detail));
            }
        }

        if (req.getUsers() == null) {
            req.setUsers(new HashSet<>());
        }
//        Set<User> users = new HashSet<>();
        if (!req.getUsers().contains(user)) {
            req.getUsers().add(user);
        }
//        users.add(user);
//        req.setUsers(users);
//        req.setUsers(new HashSet<>(Collections.singletonList(user)));

        Order order = OrderMapper.INSTANCE.mapReqToEntity(req);
//        order.addUser(user);

        orderRepository.save(order);

        return OrderMapper.INSTANCE.mapEntityToDTO(order);
    }

    @Override
    public Page<OrderDTO> getOrders(Pageable pageable, Integer status) {
        Page<Order> orders = orderRepository.findOrders(pageable, status);
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
}
