package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.Category;
import com.example.project_sem_4.model.dto.CategoryDTO;
import com.example.project_sem_4.model.mapper.CategoryMapper;
import com.example.project_sem_4.model.req.CategoryReq;
import com.example.project_sem_4.repository.CategoryRepository;
import com.example.project_sem_4.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public CategoryDTO createCategory(CategoryReq req) {

        if (req == null) {
            throw new RuntimeException("NullPointerException");
        }
        if (req.getId() == null) {
            Category category = categoryRepository.findByName(req.getName());
            if (category != null) {
                throw new RuntimeException("Category is already in use");
            }
        }
        Category category = CategoryMapper.INSTANCE.mapReqToEntity(req);
        categoryRepository.save(category);
        return CategoryMapper.INSTANCE.mapEntityToDTO(category);
    }

    @Override
    public Page<CategoryDTO> getCategories(Pageable pageable, String name, String description, Integer status) {
        Page<Category> categories = categoryRepository.findCategories(pageable, name, description, status);
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
}
