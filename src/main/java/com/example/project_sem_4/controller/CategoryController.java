package com.example.project_sem_4.controller;

import com.example.project_sem_4.model.dto.CategoryDTO;
import com.example.project_sem_4.model.req.CategoryReq;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "Category management APIs")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCategory(@ModelAttribute CategoryReq req) throws IOException {
        CategoryDTO create = categoryService.createCategory(req);
        return ResponseEntity.ok(create);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@ModelAttribute CategoryReq req, @PathVariable Long id) throws IOException {
        req.setId(id);
        CategoryDTO update = categoryService.createCategory(req);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete Success");
    }

    @PostMapping("/list")
    public ResponseEntity<?> getCategories(@ModelAttribute CategoryReq req) {
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<CategoryDTO> page = categoryService.getCategories(
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
    public ResponseEntity<?> getAllCategories() {
        Pageable pageable = PageRequest.of(0,20);
        Page<CategoryDTO> categories = categoryService.getAllCategories(pageable);
        DataRes res = new DataRes();
        res.setData(categories.getContent());
        res.setPagination(new Pagination(categories.getPageable().getPageNumber(), categories.getSize(), categories.getTotalElements()));
        return ResponseEntity.ok(res);
    }
}
