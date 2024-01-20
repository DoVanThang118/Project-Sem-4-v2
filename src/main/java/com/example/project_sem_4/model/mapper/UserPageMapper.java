package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserPageMapper extends  MapperEntity<UserDTO, User>{
    UserPageMapper INSTANCE = Mappers.getMapper(UserPageMapper.class);
}
