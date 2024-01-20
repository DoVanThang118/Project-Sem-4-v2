package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.Brand;
import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.mapper.BrandMapper;
import com.example.project_sem_4.model.req.BrandReq;
import com.example.project_sem_4.repository.BrandRepository;
import com.example.project_sem_4.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Page<BrandDTO> getBrand(Pageable pageable, String name, String description, String hotline, String email) {
        Page<Brand> brands = brandRepository.findBrands(pageable, name, description, hotline, email);
        return BrandMapper.pageDTO(brands);
    }

    @Override
    public BrandDTO saveBrand(BrandReq req) {
        if (req == null) {
            throw new RuntimeException("NullPointerException");
        }
        if (req.getId() == null ) {
            Brand brand = brandRepository.findByName(req.getName());
            if (brand != null) {
                throw new RuntimeException("Brand is already in use");
            }
        }
        Brand brand = BrandMapper.toBrand(req);
        brandRepository.save(brand);
        return BrandMapper.brandDTO(brand);
    }

    @Override
    public void deleteBrand(Long id) {
        Optional<Brand> brand = brandRepository.findById(id);
        if (brand.isEmpty()) {
            throw new RuntimeException("Not found brand has id = " + id);
        }
        try {
            brandRepository.delete(brand.get());
        } catch (Exception e) {
            throw new RuntimeException("Database error. Can't delete role");
        }
    }
}
