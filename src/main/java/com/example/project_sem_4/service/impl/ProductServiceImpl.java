package com.example.project_sem_4.service.impl;

import com.cloudinary.Cloudinary;
import com.example.project_sem_4.entity.*;
import com.example.project_sem_4.model.dto.ProductDTO;
import com.example.project_sem_4.model.mapper.BrandMapper;
import com.example.project_sem_4.model.mapper.ProductMapper;
import com.example.project_sem_4.model.req.ProductReq;
import com.example.project_sem_4.repository.CategoryRepository;
import com.example.project_sem_4.repository.ImageRepository;
import com.example.project_sem_4.repository.ProductRepository;
import com.example.project_sem_4.repository.RestaurantRepository;
import com.example.project_sem_4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ProductDTO createProduct(ProductReq req, User user) throws IOException {
        if (req == null) {
            throw new RuntimeException("NullPointerException");
        }
        if (req.getId() == null ) {
            Product product = productRepository.findByName(req.getName());
            if (product != null) {
                throw new RuntimeException("Product is already in use");
            }
        }
//        if (req.getRestaurantId() != null) {
//            Optional<Restaurant> restaurant = restaurantRepository.findById(req.getRestaurantId());
//            restaurant.ifPresent(req::setRestaurant);
//        }
        Optional<Restaurant> restaurant = restaurantRepository.findById(user.getRestaurant().getId());
        restaurant.ifPresent(req::setRestaurant);

        if (req.getCategoryId() != null) {
            Optional<Category> category = categoryRepository.findById(req.getCategoryId());
            category.ifPresent(req::setCategory);
        }

        req.setStatus(req.getStatus() == null ? 1 : req.getStatus());

        if (req.getImg() != null) {
            Set<Image> files = new HashSet<>();
            for (MultipartFile file : req.getImg()) {
                Image imageReq = new Image();
                String url = cloudinary.uploader().upload(
                                file.getBytes(),
                                Map.of("public_id", UUID.randomUUID().toString()))
                        .get("url").toString();
                imageReq.setUrl(url);
                imageReq.setStatus(1);
                imageRepository.save(imageReq);
                files.add(imageReq);
            }
            req.setImages(files);
        }
        Product product = ProductMapper.INSTANCE.mapReqToEntity(req);
        productRepository.save(product);
        return ProductMapper.INSTANCE.mapEntityToDTO(product);
    }

    @Override
    public Page<ProductDTO> getProducts(Pageable pageable, Long id, String name, String description, Double price, Integer status, String type, Integer rate, Long restaurantId, Long categoryId) {
        Page<Product> products = productRepository.findProducts(pageable, id, name, description, price, status, type, rate, restaurantId, categoryId);
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
        Page<Product> products = productRepository.findAllProducts(pageable);
        return products.map(ProductMapper.INSTANCE::mapEntityToDTO);
    }
}
