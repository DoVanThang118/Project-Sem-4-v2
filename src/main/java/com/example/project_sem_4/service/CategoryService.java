package com.example.project_sem_4.service;

import com.example.project_sem_4.model.dto.CategoryDTO;
import com.example.project_sem_4.model.req.CategoryReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface CategoryService {

    CategoryDTO createCategory(CategoryReq req) throws IOException;

    CategoryDTO updateCategory(CategoryReq req, Long id) throws IOException;

    CategoryDTO updateAvatar(CategoryReq req, Long id) throws IOException;


    Page<CategoryDTO> getCategories(Pageable pageable, Long id, String name, String description, Integer status);

    void deleteCategory(Long id);

    Page<CategoryDTO> getAllCategories(Pageable pageable);
}
