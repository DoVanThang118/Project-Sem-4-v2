package com.example.project_sem_4.service;

import com.example.project_sem_4.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public interface GifService {
    String uploadFile(MultipartFile multipartFile);

    File convertMultiPartToFile(MultipartFile file) throws IOException;

    LinkedHashMap<String, Object> modifyJsonResponse(String requestType, String url);

    Gif findGifByIdAndUser(Long id, User currentUser);

    void deleteGif(Gif gif, User currentUser);

    Gif findGifById(Long id);

    void saveGifToDB(String url, String title, User currentUser);
    List<Gif> findGifByUser(User currentUser);

    Gif saveGifForRestaurant(String url, Restaurant restaurant);
    Gif saveGifForCategory(String url, Category category);

    Gif saveGifForProduct(String url, Product product);

    Gif saveGifForBrand(String url, Brand brand);
}
