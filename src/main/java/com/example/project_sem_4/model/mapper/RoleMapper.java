package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.Role;
import com.example.project_sem_4.model.dto.RoleDTO;
import com.example.project_sem_4.model.req.RoleReq;
import org.mapstruct.factory.Mappers;

public interface RoleMapper extends MapperEntity<RoleDTO, Role>{

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role mapReqToEntity(RoleReq req);
}
