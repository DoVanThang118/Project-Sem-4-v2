package com.example.project_sem_4.controller;

import com.example.project_sem_4.model.dto.ProductDTO;
import com.example.project_sem_4.model.req.ProductReq;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.service.ProductService;
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
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveProduct(@ModelAttribute ProductReq req) throws IOException {
        ProductDTO create = productService.createProduct(req);
        return ResponseEntity.ok(create);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@ModelAttribute ProductReq req, @PathVariable Long id) throws IOException {
        req.setId(id);
        ProductDTO update = productService.createProduct(req);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Delete Success");
    }

    @PostMapping("/list")
    public ResponseEntity<?> getProducts(@RequestBody ProductReq req) {
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<ProductDTO> page = productService.getProducts(
                pageable,
                req.getId(),
                req.getName(),
                req.getDescription(),
                req.getPrice(),
                req.getStatus(),
                req.getType(),
                req.getRate(),
                req.getCategoryId()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProducts() {
        Pageable pageable = PageRequest.of(0,20);
        Page<ProductDTO> products = productService.getAllProducts(pageable);
        DataRes res = new DataRes();
        res.setData(products.getContent());
        res.setPagination(new Pagination(products.getPageable().getPageNumber(), products.getSize(), products.getTotalElements()));
        return ResponseEntity.ok(res);
    }
}
