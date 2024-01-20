package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.Order;
import com.example.project_sem_4.model.dto.OrderDTO;
import com.example.project_sem_4.model.req.OrderReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper extends MapperEntity<OrderDTO, Order> {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order mapReqToEntity(OrderReq req);
}
