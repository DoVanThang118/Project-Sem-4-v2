package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.Role;
import com.example.project_sem_4.model.dto.RoleDTO;
import com.example.project_sem_4.model.req.RoleReq;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;

public class RoleMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static RoleDTO roleDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    public static Role toRole(RoleReq req) {
        return modelMapper.map(req, Role.class);
    }

    public static Page<RoleDTO> pageDTO(Page<Role> roles) {
        TypeToken<Page<RoleDTO>> typeToken = new TypeToken<>() {};
        return modelMapper.map(roles, typeToken.getType());
    }
}
