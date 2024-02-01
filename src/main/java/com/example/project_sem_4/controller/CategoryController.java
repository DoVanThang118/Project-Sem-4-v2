package com.example.project_sem_4.controller;

import com.example.project_sem_4.entity.Gif;
import com.example.project_sem_4.model.dto.CategoryDTO;
import com.example.project_sem_4.model.mapper.BrandMapper;
import com.example.project_sem_4.model.mapper.CategoryMapper;
import com.example.project_sem_4.model.req.CategoryReq;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.service.CategoryService;
import com.example.project_sem_4.service.GifService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category", description = "Category management APIs")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GifService gifService;

    @PostMapping("/create")
    public ResponseEntity<?> saveCategory(@ModelAttribute CategoryReq req) {
        List<Gif> files = new ArrayList<>();
        CategoryDTO create = categoryService.createCategory(req);
        if (req.getImg() != null) {
            for (MultipartFile file : req.getImg()) {
                String url = gifService.uploadFile(file);
                Gif gif = gifService.saveGifForCategory(url, CategoryMapper.INSTANCE.mapDTOToEntity(create));
                files.add(gif);
            }
            create.setGifs(files);
        }
        return ResponseEntity.ok(create);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateCategory(@ModelAttribute CategoryReq req, @PathVariable Long id) {
        req.setId(id);
        CategoryDTO update = categoryService.createCategory(req);
        List<Gif> files = new ArrayList<>();
        if (req.getImg() != null) {
            for (MultipartFile file : req.getImg()) {
                String url = gifService.uploadFile(file);
                Gif gif = gifService.saveGifForCategory(url, CategoryMapper.INSTANCE.mapDTOToEntity(update));
                files.add(gif);
            }
            update.setGifs(files);
        }
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete Success");
    }

    @PostMapping("/List")
    public ResponseEntity<?> getCategories(@RequestBody CategoryReq req) {
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<CategoryDTO> page = categoryService.getCategories(
                pageable,
                req.getName(),
                req.getDescription(),
                req.getStatus()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }
}
