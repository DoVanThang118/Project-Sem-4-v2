package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.OrderDetail;
import com.example.project_sem_4.model.dto.OrderDetailDTO;
import com.example.project_sem_4.model.req.OrderDetailReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper
public interface OrderDetailMapper extends MapperEntity<OrderDetailDTO, OrderDetail> {

    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    OrderDetail mapReqToEntity(OrderDetailReq req);
}
