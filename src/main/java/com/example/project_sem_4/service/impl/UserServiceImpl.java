package com.example.project_sem_4.service.impl;

import com.cloudinary.Cloudinary;
import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.entity.Restaurant;
import com.example.project_sem_4.entity.Role;
import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.UserDTO;
import com.example.project_sem_4.model.mapper.UserMapper;
import com.example.project_sem_4.model.mapper.UserPageMapper;
import com.example.project_sem_4.model.req.UserReq;
import com.example.project_sem_4.repository.ImageRepository;
import com.example.project_sem_4.repository.RestaurantRepository;
import com.example.project_sem_4.repository.RoleRepository;
import com.example.project_sem_4.repository.UserRepository;
import com.example.project_sem_4.service.ImageService;
import com.example.project_sem_4.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ImageRepository imageRepository;

    private final RestaurantRepository restaurantRepository;

    private final Cloudinary cloudinary;

    private final ImageService imageService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ImageRepository imageRepository, RestaurantRepository restaurantRepository, Cloudinary cloudinary, ImageService imageService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.imageRepository = imageRepository;
        this.restaurantRepository = restaurantRepository;
        this.cloudinary = cloudinary;
        this.imageService = imageService;
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserPageMapper.INSTANCE::mapEntityToDTO);
    }

    @Override
    public UserDTO createUser(UserReq req) {
        if (req == null) {
            throw new RuntimeException("NullPointerException");
        }
        User user = userRepository.findByEmail(req.getEmail());
        if (user != null) {
            throw new RuntimeException("Email is already in use");
        }
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        if (req.getType() == null) {
            req.setType("user");
            role = roleRepository.findByName("USER");
            roles.add(role);
        }

        if (req.getType() != null) {
            role = roleRepository.findByName(req.getType().toUpperCase(Locale.ROOT));
            roles.add(role);
            req.setRoles(roles);
        }

        if (req.getRestaurantId() != null) {
            Restaurant restaurant = restaurantRepository.findById(req.getRestaurantId()).orElseThrow(() -> new RuntimeException("restaurant not found"));
            req.setRestaurant(restaurant);
        }

        user = UserMapper.toUser(req);
        user.setStatus(1);
        userRepository.save(user);

        return UserMapper.userDto(user);
    }

    @Override
    public UserDTO updateUser(UserReq req, Long id) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (req.getName() != null) {
            user.setName(req.getName());
        }
        if (req.getEmail() != null) {
            user.setEmail(req.getEmail());
        }
        if (req.getAddress() != null) {
            user.setAddress(req.getAddress());
        }
        if (req.getTel() != null) {
            user.setTel(req.getTel());
        }
        if (req.getBirthday() != null) {
            user.setBirthday(req.getBirthday());
        }
        try {
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Database error. Can't update user");
        }
        return UserMapper.userDto(user);
    }

    @Override
    public UserDTO updateAvatar(UserReq req, Long id) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

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

        user.setImages(files);
        userRepository.save(user);

        return UserMapper.userDto(user);
    }

    @Override
    public UserDTO getProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.userDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("Not Found User");
        }
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Database error. Can't delete user");
        }
    }

    @Override
    public User findByEmail(String name) {
        User user = userRepository.findByEmail(name);
        if (user == null) {
            throw new RuntimeException("Not Found In System");
        }
        return user;
    }

    @Override
    public Page<UserDTO> getUsers(Pageable pageable, Long id, String name, String email, String tel, String address, LocalDate birthday, String type, Long restaurantId, Integer status) {
        Page<User> users = userRepository.findUsers(pageable, id, name, email, tel ,address, birthday, type, restaurantId, status);
        return users.map(UserPageMapper.INSTANCE::mapEntityToDTO);
    }
}
