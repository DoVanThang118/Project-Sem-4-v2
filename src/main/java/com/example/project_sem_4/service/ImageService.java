package com.example.project_sem_4.service;

import com.example.project_sem_4.model.dto.ImageDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageService {
    ImageDTO createImage(MultipartFile multipartFile) throws IOException;
}
