package com.example.project_sem_4.service;

import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.req.BrandReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public interface BrandService {
    Page<BrandDTO> getBrand(Pageable pageable, Long id, String name, String description, String hotline, String email, Integer status);

    BrandDTO saveBrand(BrandReq req) throws IOException;

    BrandDTO updateBrand(BrandReq req, Long id) throws IOException;

    BrandDTO updateAvatar(BrandReq req, Long id) throws IOException;

    void deleteBrand(Long id);

    Page<BrandDTO> getAllBrands(Pageable pageable);

}
