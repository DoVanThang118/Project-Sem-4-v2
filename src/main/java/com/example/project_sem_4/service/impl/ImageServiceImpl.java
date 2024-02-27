package com.example.project_sem_4.service.impl;

import com.cloudinary.Cloudinary;
import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.model.dto.ImageDTO;
import com.example.project_sem_4.model.mapper.ImageMapper;
import com.example.project_sem_4.model.req.ImageReq;
import com.example.project_sem_4.repository.ImageRepository;
import com.example.project_sem_4.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ImageRepository imageRepository;


    @Override
    public ImageDTO createImage(MultipartFile multipartFile) throws IOException {
        ImageReq req = new ImageReq();
        String url = cloudinary.uploader().upload(
                multipartFile.getBytes(),
                Map.of("public_id", UUID.randomUUID().toString()))
                .get("url").toString();
        req.setUrl(url);
        req.setStatus(1);
        Image image = ImageMapper.INSTANCE.mapReqToEntity(req);
        imageRepository.save(image);
        return ImageMapper.INSTANCE.mapEntityToDTO(image);
    }
}
