package com.example.project_sem_4.service;


import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.UserDTO;
import com.example.project_sem_4.model.req.UserReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface UserService {
    Page<UserDTO> getAllUsers(Pageable pageable);

    UserDTO createUser(UserReq req);

    UserDTO updateUser(UserReq req, Long id);

    void deleteUser(Long id);

    User findByEmail(String name);

    Page<UserDTO> getUsers(Pageable pageable, Long id, String name, String email, String tel, String address, Date birthday, String type, Integer status);
}
