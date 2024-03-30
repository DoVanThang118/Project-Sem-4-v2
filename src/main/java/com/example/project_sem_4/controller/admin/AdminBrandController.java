package com.example.project_sem_4.controller.admin;


import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.dto.UserDTO;
import com.example.project_sem_4.model.req.BrandReq;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.service.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/brands")
@Tag(name = "Admin Brand", description = "Brand Admin management APIs")
public class AdminBrandController {

    @Autowired
    private BrandService brandService;

    @PostMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBrand(@RequestBody BrandReq req) {
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<BrandDTO> page = brandService.getBrand(
                pageable,
                req.getId(),
                req.getName(),
                req.getDescription(),
                req.getHotline(),
                req.getEmail(),
                req.getStatus()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllBrands() {
        Pageable pageable = PageRequest.of(0,20);
        Page<BrandDTO> brands = brandService.getAllBrands(pageable);
        DataRes res = new DataRes();
        res.setData(brands.getContent());
        res.setPagination(new Pagination(brands.getPageable().getPageNumber(), brands.getSize(), brands.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveBrand(@ModelAttribute BrandReq req) throws IOException {
        BrandDTO create = brandService.saveBrand(req);
        return ResponseEntity.ok(create);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBrand(@RequestBody BrandReq req, @PathVariable Long id) throws IOException {
        req.setId(id);
        BrandDTO update = brandService.updateBrand(req, id);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @PutMapping("/avatar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAvatar(@ModelAttribute BrandReq req, @PathVariable Long id) throws IOException {
        BrandDTO result = brandService.updateAvatar(req, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok("Delete Success");
    }

}
