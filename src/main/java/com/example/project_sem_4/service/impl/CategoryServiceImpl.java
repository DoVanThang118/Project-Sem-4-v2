package com.example.project_sem_4.service.impl;

import com.cloudinary.Cloudinary;
import com.example.project_sem_4.entity.Category;
import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.entity.Product;
import com.example.project_sem_4.model.dto.CategoryDTO;
import com.example.project_sem_4.model.mapper.CategoryMapper;
import com.example.project_sem_4.model.mapper.ProductMapper;
import com.example.project_sem_4.model.req.CategoryReq;
import com.example.project_sem_4.repository.CategoryRepository;
import com.example.project_sem_4.repository.ImageRepository;
import com.example.project_sem_4.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public CategoryDTO createCategory(CategoryReq req) throws IOException {

        if (req == null) {
            throw new RuntimeException("NullPointerException");
        }
        if (req.getId() == null) {
            Category category = categoryRepository.findByName(req.getName());
            if (category != null) {
                throw new RuntimeException("Category is already in use");
            }
        }

        if (req.getStatus() == null) {
            req.setStatus(1);
        }

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
        Category category = CategoryMapper.INSTANCE.mapReqToEntity(req);
        categoryRepository.save(category);
        return CategoryMapper.INSTANCE.mapEntityToDTO(category);
    }

    @Override
    public Page<CategoryDTO> getCategories(Pageable pageable, Long id, String name, String description, Integer status) {
        Page<Category> categories = categoryRepository.findCategories(pageable, id, name, description, status);
        return categories.map(CategoryMapper.INSTANCE::mapEntityToDTO);
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new RuntimeException("Not found category has id = " + id);
        }
        try {
            categoryRepository.delete(category.get());
        } catch (Exception e) {
            throw new RuntimeException("Database error. Can't delete Category");
        }
    }

    @Override
    public Page<CategoryDTO> getAllCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories.map(CategoryMapper.INSTANCE::mapEntityToDTO);
    }
}
