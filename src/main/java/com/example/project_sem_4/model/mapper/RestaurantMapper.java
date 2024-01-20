package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.Restaurant;
import com.example.project_sem_4.model.dto.RestaurantDTO;
import com.example.project_sem_4.model.req.RestaurantReq;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface RestaurantMapper extends MapperEntity<RestaurantDTO, Restaurant> {

    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    Restaurant mapReqToEntity(RestaurantReq req);

}
