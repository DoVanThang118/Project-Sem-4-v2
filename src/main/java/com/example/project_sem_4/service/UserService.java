package com.example.project_sem_4.service;


import com.example.project_sem_4.model.dto.UserDto;
import com.example.project_sem_4.model.request.CreateUserReq;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public UserDto createUser(CreateUserReq req);
}
