package com.example.project_sem_4.service;

import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.req.BrandReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {
    Page<BrandDTO> getBrand(Pageable pageable, Long id, String name, String description, String hotline, String email);

    BrandDTO saveBrand(BrandReq req);

    void deleteBrand(Long id);

    Page<BrandDTO> getAllBrands(Pageable pageable);
}
