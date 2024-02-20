package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.Brand;
import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.dto.UserDTO;
import com.example.project_sem_4.model.mapper.BrandMapper;
import com.example.project_sem_4.model.mapper.UserMapper;
import com.example.project_sem_4.model.req.BrandReq;
import com.example.project_sem_4.repository.BrandRepository;
import com.example.project_sem_4.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Page<BrandDTO> getBrand(Pageable pageable, Long id, String name, String description, String hotline, String email) {
        Page<Brand> brands = brandRepository.findBrands(pageable, id, name, description, hotline, email);
        return brands.map(BrandMapper.INSTANCE::mapEntityToDTO);
    }

    @Override
    public Page<BrandDTO> getAllBrands(Pageable pageable) {
        Page<Brand> brands = brandRepository.findAll(pageable);
        return brands.map(BrandMapper.INSTANCE::mapEntityToDTO);
    }

    @Override
    public BrandDTO saveBrand(BrandReq req) {
        if (req == null) {
            throw new RuntimeException("NullPointerException");
        }
        if (req.getId() == null ) {
            Brand find = brandRepository.findByName(req.getName());
            if (find != null) {
                throw new RuntimeException("Brand is already in use");
            }
        }
        Brand brand = BrandMapper.INSTANCE.mapReqToEntity(req);
        brandRepository.save(brand);
        return BrandMapper.INSTANCE.mapEntityToDTO(brand);
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