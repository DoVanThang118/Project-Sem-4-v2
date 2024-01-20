package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.model.dto.CartDTO;
import com.example.project_sem_4.model.req.CartReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartMapper extends MapperEntity<CartDTO, Cart> {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    Cart mapReqToEntity(CartReq req);
}
