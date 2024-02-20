package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.Brand;
import com.example.project_sem_4.entity.Product;
import com.example.project_sem_4.model.dto.ProductDTO;
import com.example.project_sem_4.model.mapper.BrandMapper;
import com.example.project_sem_4.model.mapper.ProductMapper;
import com.example.project_sem_4.model.req.ProductReq;
import com.example.project_sem_4.repository.ProductRepository;
import com.example.project_sem_4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO createProduct(ProductReq req) {
        if (req == null) {
            throw new RuntimeException("NullPointerException");
        }
        if (req.getId() == null ) {
            Product product = productRepository.findByName(req.getName());
            if (product != null) {
                throw new RuntimeException("Product is already in use");
            }
        }
        Product product = ProductMapper.INSTANCE.mapReqToEntity(req);
        productRepository.save(product);
        return ProductMapper.INSTANCE.mapEntityToDTO(product);
    }

    @Override
    public Page<ProductDTO> getProducts(Pageable pageable, Long id, String name, String description, Double price, Integer status, String type, Integer rate, String category) {
        Page<Product> products = productRepository.findProducts(pageable, id, name, description, price, status, type, rate, category);
        return products.map(ProductMapper.INSTANCE::mapEntityToDTO);
    }

    @Override
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new RuntimeException("Not found product has id = " + id);
        }
        try {
            productRepository.delete(product.get());
        } catch (Exception e) {
            throw new RuntimeException("Database error. Can't delete product");
        }
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductMapper.INSTANCE::mapEntityToDTO);
    }
}
