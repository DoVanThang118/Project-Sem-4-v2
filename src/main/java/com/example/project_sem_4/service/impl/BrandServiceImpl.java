package com.example.project_sem_4.service.impl;

import com.cloudinary.Cloudinary;
import com.example.project_sem_4.entity.Brand;
import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.dto.ImageDTO;
import com.example.project_sem_4.model.dto.UserDTO;
import com.example.project_sem_4.model.mapper.BrandMapper;
import com.example.project_sem_4.model.mapper.ImageMapper;
import com.example.project_sem_4.model.mapper.UserMapper;
import com.example.project_sem_4.model.req.BrandReq;
import com.example.project_sem_4.model.req.ImageReq;
import com.example.project_sem_4.repository.BrandRepository;
import com.example.project_sem_4.repository.ImageRepository;
import com.example.project_sem_4.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private Cloudinary cloudinary;

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
    public BrandDTO saveBrand(BrandReq req) throws IOException {
        if (req == null) {
            throw new RuntimeException("NullPointerException");
        }
        if (req.getId() == null ) {
            Brand find = brandRepository.findByName(req.getName());
            if (find != null) {
                throw new RuntimeException("Brand is already in use");
            }
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
