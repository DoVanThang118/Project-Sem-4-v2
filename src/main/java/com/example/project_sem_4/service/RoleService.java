package com.example.project_sem_4.service;

import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.dto.RoleDTO;
import com.example.project_sem_4.model.req.RoleReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {

    Page<RoleDTO> getRole(Pageable pageable, Long id, String name, String description, Integer status);

    RoleDTO saveRole(RoleReq req);

    void deleteRole(Long id);

    Page<RoleDTO> getAllRoles(Pageable pageable);
}
