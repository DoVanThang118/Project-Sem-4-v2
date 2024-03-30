package com.example.project_sem_4.controller.admin;

import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.dto.CategoryDTO;
import com.example.project_sem_4.model.req.BrandReq;
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
@RequestMapping("/api/admin/categories")
@Tag(name = "Admin Category", description = "Admin Category management APIs")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> saveCategory(@ModelAttribute CategoryReq req) throws IOException {
        CategoryDTO create = categoryService.createCategory(req);
        return ResponseEntity.ok(create);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> updateBrand(@RequestBody CategoryReq req, @PathVariable Long id) throws IOException {
        CategoryDTO update = categoryService.updateCategory(req, id);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @PutMapping("/avatar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> updateAvatar(@ModelAttribute CategoryReq req, @PathVariable Long id) throws IOException {
        CategoryDTO result = categoryService.updateAvatar(req, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete Success");
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> getCategories(@RequestBody CategoryReq req) {
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> getAllCategories() {
        Pageable pageable = PageRequest.of(0,20);
        Page<CategoryDTO> categories = categoryService.getAllCategories(pageable);
        DataRes res = new DataRes();
        res.setData(categories.getContent());
        res.setPagination(new Pagination(categories.getPageable().getPageNumber(), categories.getSize(), categories.getTotalElements()));
        return ResponseEntity.ok(res);
    }
}
