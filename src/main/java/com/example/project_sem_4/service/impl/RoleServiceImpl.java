package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.Brand;
import com.example.project_sem_4.entity.Role;
import com.example.project_sem_4.model.dto.RoleDTO;
import com.example.project_sem_4.model.mapper.BrandMapper;
import com.example.project_sem_4.model.mapper.ProductMapper;
import com.example.project_sem_4.model.mapper.RoleMapper;
import com.example.project_sem_4.model.req.RoleReq;
import com.example.project_sem_4.repository.RoleRepository;
import com.example.project_sem_4.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Page<RoleDTO> getRole(Pageable pageable, Long id, String name, String description, Integer status) {
        Page<Role> roles = roleRepository.findRoles(pageable, id, name, description, status);
        return roles.map(RoleMapper.INSTANCE::mapEntityToDTO);
    }

    @Override
    public RoleDTO saveRole(RoleReq req) {
        if (req == null) {
            throw new RuntimeException("NullPointerException");
        }
        Role role = RoleMapper.INSTANCE.mapReqToEntity(req);
        role.setStatus(1);
        roleRepository.save(role);
        return RoleMapper.INSTANCE.mapEntityToDTO(role);
    }

    @Override
    public void deleteRole(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new RuntimeException("Not found role has id = " + id);
        }
        try {
            roleRepository.delete(role.get());
        } catch (Exception e) {
            throw new RuntimeException("Database error. Can't delete role");
        }
    }

    @Override
    public Page<RoleDTO> getAllRoles(Pageable pageable) {
        Page<Role> roles = roleRepository.findAll(pageable);
        return roles.map(RoleMapper.INSTANCE::mapEntityToDTO);
    }
}
