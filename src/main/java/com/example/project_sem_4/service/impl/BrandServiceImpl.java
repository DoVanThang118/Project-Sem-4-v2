package com.example.project_sem_4.service.impl;

import com.cloudinary.Cloudinary;
import com.example.project_sem_4.entity.Brand;
import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.mapper.BrandMapper;
import com.example.project_sem_4.model.req.BrandReq;
import com.example.project_sem_4.repository.BrandRepository;
import com.example.project_sem_4.repository.ImageRepository;
import com.example.project_sem_4.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public Page<BrandDTO> getBrand(Pageable pageable, Long id, String name, String description, String hotline, String email, Integer status) {
        Page<Brand> brands = brandRepository.findBrands(pageable, id, name, description, hotline, email, status);
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
        req.setStatus(req.getStatus() == null ? 1 : req.getStatus());

        if (req.getImg() != null) {
            req.setImages(new HashSet<>());
            Set<Image> files = new HashSet<>();
            for (MultipartFile file : req.getImg()) {
                Image imageReq = new Image();
                String url = cloudinary.uploader().upload(
                                file.getBytes(),
                                Map.of("public_id", UUID.randomUUID().toString()))
                        .get("url").toString();
                imageReq.setUrl(url);
                imageReq.setTitle(req.getName());
                imageReq.setStatus(1);
                Image image = imageRepository.save(imageReq);
                files.add(image);
            }
            req.setImages(files);
        }
        Brand brand = BrandMapper.INSTANCE.mapReqToEntity(req);
        brandRepository.save(brand);
        return BrandMapper.INSTANCE.mapEntityToDTO(brand);
    }

    @Override
    public BrandDTO updateBrand(BrandReq req, Long id) throws IOException {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));
        if (req.getName() != null) {
            brand.setName(req.getName());
        }
        if (req.getDescription() != null) {
            brand.setDescription(req.getDescription());
        }
        if (req.getHotline() != null) {
            brand.setHotline(req.getHotline());
        }
        if (req.getEmail() != null) {
            brand.setEmail(req.getEmail());
        }
        if (req.getStatus() != null) {
            brand.setStatus(req.getStatus());
        }
        try {
            brandRepository.save(brand);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Database error. Can't update Brand");
        }
        return BrandMapper.INSTANCE.mapEntityToDTO(brand);
    }

    @Override
    public BrandDTO updateAvatar(BrandReq req, Long id) throws IOException {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));

        Set<Image> files = new HashSet<>();
        for (MultipartFile file : req.getImg()) {
            Image imageReq = new Image();
            String url = cloudinary.uploader().upload(
                            file.getBytes(),
                            Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url").toString();
            imageReq.setUrl(url);
            imageReq.setTitle(req.getName());
            imageReq.setStatus(1);
            Image image = imageRepository.save(imageReq);
            files.add(image);
        }
        brand.setImages(files);
        brandRepository.save(brand);

        return BrandMapper.INSTANCE.mapEntityToDTO(brand);
    }

    @Override
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found brand has id = " + id));
        try {
            brandRepository.delete(brand);
        } catch (Exception e) {
            throw new RuntimeException("Database error. Can't delete brand");
        }
    }
}
