package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.Role;
import com.example.project_sem_4.model.dto.RoleDTO;
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
    public Page<RoleDTO> getRole(Pageable pageable, String name, String description, Integer status) {
        Page<Role> roles = roleRepository.findRoles(pageable, name, description, status);
        return RoleMapper.pageDTO(roles);
    }

    @Override
    public RoleDTO saveRole(RoleReq req) {
        if (req == null) {
            throw new RuntimeException("NullPointerException");
        }
        Role role = RoleMapper.toRole(req);
        role.setStatus(1);
        roleRepository.save(role);
        return RoleMapper.roleDTO(role);
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


}
