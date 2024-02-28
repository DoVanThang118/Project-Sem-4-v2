package com.example.project_sem_4.controller;

import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.dto.RoleDTO;
import com.example.project_sem_4.model.req.RoleReq;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/roles")
@Tag(name = "Role", description = "Role management APIs")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DataRes> getRoles(@RequestBody RoleReq req) {
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<RoleDTO> page = roleService.getRole(
                pageable,
                req.getId(),
                req.getName(),
                req.getDescription(),
                req.getStatus()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllRoles() {
        Pageable pageable = PageRequest.of(0,20);
        Page<RoleDTO> roles = roleService.getAllRoles(pageable);
        DataRes res = new DataRes();
        res.setData(roles.getContent());
        res.setPagination(new Pagination(roles.getPageable().getPageNumber(), roles.getSize(), roles.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveRole(@RequestBody RoleReq req) {
        RoleDTO create = roleService.saveRole(req);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRole(@RequestBody RoleReq req, @PathVariable Long id) {
        req.setId(id);
        RoleDTO create = roleService.saveRole(req);
        return new ResponseEntity<>(create, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Delete Success");
    }
}
