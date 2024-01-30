package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.entity.OrderDetail;
import com.example.project_sem_4.entity.Product;
import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.OrderDetailDTO;
import com.example.project_sem_4.model.mapper.OrderDetailMapper;
import com.example.project_sem_4.model.req.OrderDetailReq;
import com.example.project_sem_4.repository.CartRepository;
import com.example.project_sem_4.repository.OrderDetailRepository;
import com.example.project_sem_4.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository oderDetailRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<OrderDetailDTO> create(User user) {
        List<Cart> carts = cartRepository.findByUser(user);
        if (carts == null) {
            throw new RuntimeException("Cart null");
        }
        List<OrderDetail> item = new ArrayList<>();

        List<OrderDetailDTO> itemDTO = new ArrayList<>();

        for (Cart cart : carts) {

            OrderDetailReq req = new OrderDetailReq();

            req.setQuantity(cart.getQty());

            Product product = cart.getProduct();
            req.setPrice(product.getPrice());

            req.setSum(cart.getQty()* product.getPrice());

            req.setStatus(1);

            OrderDetail orderDetail = OrderDetailMapper.INSTANCE.mapReqToEntity(req);

            user.addOrderDetail(orderDetail);

            item.add(orderDetail);
        }

        for (OrderDetail orderDetail : item) {
            itemDTO.add(OrderDetailMapper.INSTANCE.mapEntityToDTO(orderDetail));
        }

        return itemDTO;
    }

    @Override
    public Page<OrderDetailDTO> getOrderDetails(Pageable pageable, Integer status) {
        Page<OrderDetail> orderDetails = oderDetailRepository.findOrderDetails(pageable, status);
        return orderDetails.map(OrderDetailMapper.INSTANCE::mapEntityToDTO);
    }

    @Override
    public void deleteOrderDetail(Long id) {
        Optional<OrderDetail> orderDetail = oderDetailRepository.findById(id);
        if (orderDetail.isEmpty()) {
            throw new RuntimeException("Not found cart has id = " + id);
        }
        try {
            oderDetailRepository.delete(orderDetail.get());
        } catch (Exception e) {
            throw new RuntimeException("Database error. Can't delete Cart");
        }
    }
}
