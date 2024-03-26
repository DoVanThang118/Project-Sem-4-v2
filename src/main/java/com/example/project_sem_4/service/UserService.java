package com.example.project_sem_4.service;


import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.UserDTO;
import com.example.project_sem_4.model.req.UserReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public interface UserService {
    Page<UserDTO> getAllUsers(Pageable pageable);

    UserDTO createUser(UserReq req);

    UserDTO updateUser(UserReq req, Long id) throws IOException;

    void deleteUser(Long id);

    User findByEmail(String name);

    Page<UserDTO> getUsers(Pageable pageable, Long id, String name, String email, String tel, String address, LocalDate birthday, String type, Long restaurantId, Integer status);

    UserDTO updateAvatar(MultipartFile file, Long id) throws IOException;
}
