package com.example.project_sem_4.service;

import com.example.project_sem_4.model.dto.ImageDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public interface ImageService {
    ImageDTO uploadImages(MultipartFile multipartFile) throws IOException;

    void deleteImage(String imageUrl) throws IOException;
}
