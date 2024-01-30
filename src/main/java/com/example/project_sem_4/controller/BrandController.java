package com.example.project_sem_4.controller;

import com.example.project_sem_4.entity.Gif;
import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.mapper.BrandMapper;
import com.example.project_sem_4.model.mapper.ProductMapper;
import com.example.project_sem_4.model.req.BrandReq;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.service.BrandService;
import com.example.project_sem_4.service.GifService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/brands")
@Tag(name = "Brand", description = "Brand management APIs")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private GifService gifService;

    @PostMapping("/list")
    public ResponseEntity<?> getBrand(@RequestBody BrandReq req) {
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<BrandDTO> page = brandService.getBrand(
                pageable,
                req.getName(),
                req.getDescription(),
                req.getHotline(),
                req.getEmail()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveBrand(@ModelAttribute BrandReq req) {
        List<Gif> files = new ArrayList<>();
        BrandDTO create = brandService.saveBrand(req);
        for (MultipartFile file : req.getImg()) {
            String url = gifService.uploadFile(file);
            Gif gif = gifService.saveGifForBrand(url, BrandMapper.INSTANCE.mapDTOToEntity(create));
            files.add(gif);
        }
        create.setGifs(files);
        return ResponseEntity.ok(create);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBrand(@ModelAttribute BrandReq req, @PathVariable Long id) {
        req.setId(id);
        BrandDTO update = brandService.saveBrand(req);
        List<Gif> files = new ArrayList<>();
        for (MultipartFile file : req.getImg()) {
            String url = gifService.uploadFile(file);
            Gif gif = gifService.saveGifForBrand(url, BrandMapper.INSTANCE.mapDTOToEntity(update));
            files.add(gif);
        }
        update.setGifs(files);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok("Delete Success");
    }
}
