package com.example.project_sem_4.service;

import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.dto.ProductDTO;
import com.example.project_sem_4.model.req.ProductReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ProductService {

    ProductDTO createProduct(ProductReq req, User user) throws IOException;

    Page<ProductDTO> getProducts(Pageable pageable, Long id, String name, String description, Double price, Integer status, String type, Integer rate, Long restaurantId, Long categoryId);

    void deleteProduct(Long id);

    Page<ProductDTO> getAllProducts(Pageable pageable);
}
