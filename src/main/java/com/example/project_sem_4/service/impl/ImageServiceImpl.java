package com.example.project_sem_4.service.impl;

import com.cloudinary.Cloudinary;
import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.model.dto.ImageDTO;
import com.example.project_sem_4.model.mapper.ImageMapper;
import com.example.project_sem_4.model.req.ImageReq;
import com.example.project_sem_4.repository.ImageRepository;
import com.example.project_sem_4.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${cloudinary.cloud_name}")
    public String cloudName;

    @Value("${cloudinary.api_key}")
    public String apiKey;

    @Value("${cloudinary.api_secret}")
    public String apiSecret;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ImageDTO uploadImages(MultipartFile multipartFile) throws IOException {

        Image image = new Image();
        String url = cloudinary.uploader().upload(
                        multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url").toString();
        image.setUrl(url);
        image.setStatus(1);
        imageRepository.save(image);

        return ImageMapper.INSTANCE.mapEntityToDTO(image);
    }

    @Override
    public void deleteImage(String imageUrl) throws IOException {

        // Xác định URL endpoint của Cloudinary để xóa ảnh
        String endpoint = "https://api.cloudinary.com/v1_1/" + cloudName + "/delete_by_token";

        // Tạo URL cho ảnh mà bạn muốn xóa
        String deleteUrl = endpoint + "?token=" + apiKey + "&url=" + imageUrl;

        // Tạo kết nối HTTP
        URL url = new URL(deleteUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        // Kiểm tra phản hồi từ Cloudinary
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Image deleted successfully");
        } else {
            System.out.println("Failed to delete image. Response code: " + responseCode);
        }
        connection.disconnect();
    }
}
