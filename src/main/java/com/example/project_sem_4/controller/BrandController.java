package com.example.project_sem_4.controller;

import com.example.project_sem_4.model.dto.BrandDTO;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brands")
@Tag(name = "Brand", description = "Brand management APIs")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @PostMapping("/list")
    public ResponseEntity<?> getBrand(@RequestBody BrandReq req) {
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<BrandDTO> page = brandService.getBrand(
                pageable,
                req.getName(),
                req.getDescription(),
                req.getHotline(),
                req.getEmail()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveBrand(@RequestBody BrandReq req) {
        BrandDTO create = brandService.saveBrand(req);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBrand(@RequestBody BrandReq req, @PathVariable Long id) {
        req.setId(id);
        BrandDTO create = brandService.saveBrand(req);
        return new ResponseEntity<>(create, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok("Delete Success");
    }
}
