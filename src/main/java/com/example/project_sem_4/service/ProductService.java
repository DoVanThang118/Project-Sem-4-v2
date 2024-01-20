package com.example.project_sem_4.service;

import com.example.project_sem_4.model.dto.ProductDTO;
import com.example.project_sem_4.model.req.ProductReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    ProductDTO createProduct(ProductReq req);

    Page<ProductDTO> getProducts(Pageable pageable, String name, String description, Double price, Integer status, String type, Integer rate, String category);

    void deleteProduct(Long id);
}
