package com.example.project_sem_4.service.impl;

import com.cloudinary.Cloudinary;
import com.example.project_sem_4.entity.*;
import com.example.project_sem_4.repository.GifRepository;
import com.example.project_sem_4.service.GifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GifServiceImpl implements GifService {
    @Autowired
    private GifRepository gifRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        try {
            File uploadedFile = convertMultiPartToFile(multipartFile);
            boolean isDeleted = uploadedFile.delete();

            if (isDeleted){
                System.out.println("File successfully deleted");
            }else
                System.out.println("File doesn't exist");

            return cloudinary.uploader().upload(
                    multipartFile.getBytes(),
                    Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    @Override
    public LinkedHashMap<String, Object> modifyJsonResponse(String requestType, String url) {
        LinkedHashMap<String, Object> jsonResponse = new LinkedHashMap<>();
        Gif gif = gifRepository.findGifByUrl(url);

        if(requestType.equals("create")){

            jsonResponse.put("status", "success");
            LinkedHashMap<String, String > data = new LinkedHashMap<>();
            data.put("id", gif.getId().toString());
            data.put("message","GIF image successfully posted");
            data.put("title", gif.getTitle());
            data.put("url", url);

            jsonResponse.put("data", data);
        }

        if(requestType.equals("delete")){
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String > data = new LinkedHashMap<>();
            data.put("message","Gif post successfully deleted");
            jsonResponse.put("data", data);
        }

        if(requestType.equals("get")){

            jsonResponse.put("status", "success");
            LinkedHashMap<String, Object > data = new LinkedHashMap<>();

            data.put("gifId", gif.getId().toString());
            data.put("title", gif.getTitle());
            data.put("url", gif.getUrl());

            jsonResponse.put("data", data);

//            CommentService.addingCommentToResponseSpec(jsonResponse, data, gif.getComments(), null);
        }
        return jsonResponse;
    }

    @Override
    public Gif findGifByIdAndUser(Long id, User currentUser) {
        return gifRepository.findGifByIdAndUser(id, currentUser);
    }

    @Override
    public void deleteGif(Gif gif, User user) {
        user.deleteGif(gif);
        gifRepository.delete(gif);
    }

    @Override
    public Gif findGifById(Long id) {
        return gifRepository.findGifById(id);
    }

    @Override
    public void saveGifToDB(String url, String title, User user) {
        Gif gif = new Gif();

        gif.setUrl(url);
        gif.setTitle(title);
        gif.setUser(user);
        gif.setStatus(1);
        user.addGif(gif);

        gifRepository.save(gif);
    }

    @Override
    public List<Gif> findGifByUser(User currentUser) {
        return gifRepository.findByUser(currentUser);
    }

    @Override
    public Gif saveGifForCategory(String url, Category category) {
        Gif gif = new Gif();

        gif.setUrl(url);
        gif.setCategory(category);
        gif.setStatus(1);
        category.addGif(gif);

        return gifRepository.save(gif);
    }

    @Override
    public Gif saveGifForProduct(String url, Product product) {
        Gif gif = new Gif();

        gif.setUrl(url);
        gif.setProduct(product);
        gif.setStatus(1);
        product.addGif(gif);

        return gifRepository.save(gif);
    }

    @Override
    public Gif saveGifForRestaurant(String url, Restaurant restaurant) {
        Gif gif = new Gif();

        gif.setUrl(url);
        gif.setRestaurant(restaurant);
        gif.setStatus(1);
        restaurant.addGif(gif);

        return gifRepository.save(gif);
    }
}
