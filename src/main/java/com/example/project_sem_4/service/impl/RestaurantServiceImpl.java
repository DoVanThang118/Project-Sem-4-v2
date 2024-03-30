package com.example.project_sem_4.service.impl;

import com.cloudinary.Cloudinary;
import com.example.project_sem_4.entity.Brand;
import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.entity.Order;
import com.example.project_sem_4.entity.Restaurant;
import com.example.project_sem_4.model.dto.RestaurantDTO;
import com.example.project_sem_4.model.mapper.BrandMapper;
import com.example.project_sem_4.model.mapper.RestaurantMapper;
import com.example.project_sem_4.model.req.RestaurantReq;
import com.example.project_sem_4.repository.BrandRepository;
import com.example.project_sem_4.repository.ImageRepository;
import com.example.project_sem_4.repository.OrderRepository;
import com.example.project_sem_4.repository.RestaurantRepository;
import com.example.project_sem_4.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public RestaurantDTO createRestaurant(RestaurantReq req) throws IOException {
        if (req == null) {
            throw new RuntimeException("NullPointerException");
        }
        if (req.getId() == null ) {
            Restaurant restaurant = restaurantRepository.findByName(req.getName());
            if (restaurant != null) {
                throw new RuntimeException("Restaurant is already in use");
            }
        }
        if (req.getBrandId() != null) {
            Optional<Brand> brand = brandRepository.findById(req.getBrandId());
            brand.ifPresent(req::setBrand);
        }

        req.setStatus(req.getStatus() == null ? 1 : req.getStatus());

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
        Restaurant restaurant = RestaurantMapper.INSTANCE.mapReqToEntity(req);
        restaurantRepository.save(restaurant);
        return RestaurantMapper.INSTANCE.mapEntityToDTO(restaurant);
    }

    @Override
    public RestaurantDTO updateRestaurant(RestaurantReq req, Long id) throws IOException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RuntimeException("restaurant not found"));
        if (req.getName() != null) {
            restaurant.setName(req.getName());
        }
        if (req.getTel() != null) {
            restaurant.setTel(req.getTel());
        }
        if (req.getMeals() != null) {
            restaurant.setMeals(req.getMeals());
        }
        if (req.getCuisines() != null) {
            restaurant.setCuisines(req.getCuisines());
        }
        if (req.getRate() != null) {
            restaurant.setRate(req.getRate());
        }
        if (req.getHourStart() != null) {
            restaurant.setHourStart(req.getHourStart());
        }
        if (req.getHourEnd() != null) {
            restaurant.setHourEnd(req.getHourEnd());
        }
        if (req.getAddress() != null) {
            restaurant.setAddress(req.getAddress());
        }
        if (req.getDescription() != null) {
            restaurant.setDescription(req.getDescription());
        }
        if (req.getStatus() != null) {
            restaurant.setStatus(req.getStatus());
        }
        if (req.getBrandId() != null) {
            Optional<Brand> brand = brandRepository.findById(req.getBrandId());
            brand.ifPresent(restaurant::setBrand);
        }
        restaurantRepository.save(restaurant);
        return RestaurantMapper.INSTANCE.mapEntityToDTO(restaurant);
    }

    @Override
    public RestaurantDTO updateAvatar(RestaurantReq req, Long id) throws IOException {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new RuntimeException("restaurant not found"));

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
        restaurant.setImages(files);
        restaurantRepository.save(restaurant);

        return RestaurantMapper.INSTANCE.mapEntityToDTO(restaurant);
    }

    @Override
    public Page<RestaurantDTO> getRestaurant(Pageable pageable, Long id, String name, String description, String tel, String address, List<String> meals, List<String> cuisines, LocalTime hourStart, LocalTime hourEnd, Double rate, Integer status) {
        Page<Restaurant> restaurants = restaurantRepository.findRestaurants(pageable, id, name, description, tel, address, meals, cuisines, hourStart, hourEnd, rate, status);
        return restaurants.map(RestaurantMapper.INSTANCE::mapEntityToDTO);
    }

    @Override
    public void deleteRestaurant(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isEmpty()) {
            throw new RuntimeException("Not found restaurant has id = " + id);
        }
        try {
            restaurantRepository.delete(restaurant.get());
        } catch (Exception e) {
            throw new RuntimeException("Database error. Can't delete Restaurant");
        }
    }

    @Override
    public Page<RestaurantDTO> getAllRestaurants(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);
        return restaurants.map(RestaurantMapper.INSTANCE::mapEntityToDTO);
    }

    @Override
    public Double totalRevenue() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        double totalRevenue = 0.0;

        for (Restaurant res : restaurants) {
            List<Order> orders = orderRepository.findAllByRestaurantId(res.getId());

            for (Order order : orders) {
                totalRevenue += order.getTotalMoney();
            }
        }

        return totalRevenue;
    }
}
