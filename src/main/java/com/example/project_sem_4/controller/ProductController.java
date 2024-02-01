package com.example.project_sem_4.controller;

import com.example.project_sem_4.entity.Gif;
import com.example.project_sem_4.model.dto.ProductDTO;
import com.example.project_sem_4.model.mapper.CategoryMapper;
import com.example.project_sem_4.model.mapper.ProductMapper;
import com.example.project_sem_4.model.req.ProductReq;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.service.GifService;
import com.example.project_sem_4.service.ProductService;
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
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private GifService gifService;

    @PostMapping("/create")
    public ResponseEntity<?> saveProduct(@ModelAttribute ProductReq req) {
        List<Gif> files = new ArrayList<>();
        ProductDTO create = productService.createProduct(req);
        if (req.getImg() != null) {
            for (MultipartFile file : req.getImg()) {
                String url = gifService.uploadFile(file);
                Gif gif = gifService.saveGifForProduct(url, ProductMapper.INSTANCE.mapDTOToEntity(create));
                files.add(gif);
            }
            create.setGifs(files);
        }
        return ResponseEntity.ok(create);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateProduct(@ModelAttribute ProductReq req, @PathVariable Long id) {
        req.setId(id);
        ProductDTO update = productService.createProduct(req);
        List<Gif> files = new ArrayList<>();
        if (req.getImg() != null) {
            for (MultipartFile file : req.getImg()) {
                String url = gifService.uploadFile(file);
                Gif gif = gifService.saveGifForProduct(url, ProductMapper.INSTANCE.mapDTOToEntity(update));
                files.add(gif);
            }
            update.setGifs(files);
        }
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Delete Success");
    }

    @PostMapping("/List")
    public ResponseEntity<?> getProducts(@RequestBody ProductReq req) {
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<ProductDTO> page = productService.getProducts(
                pageable,
                req.getName(),
                req.getDescription(),
                req.getPrice(),
                req.getStatus(),
                req.getType(),
                req.getRate(),
                req.getNameCategory()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }
}
