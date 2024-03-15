package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.CartItem;
import com.example.project_sem_4.model.dto.CartItemDTO;
import com.example.project_sem_4.model.req.CartItemReq;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartItemMapper extends MapperEntity<CartItemDTO, CartItem>{

    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    CartItem mapReqToEntity(CartItemReq req);
}
