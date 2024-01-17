package com.example.project_sem_4.service;

import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.exception.DuplicateRecordException;
import com.example.project_sem_4.model.dto.UserDto;
import com.example.project_sem_4.model.mapper.UserMapper;
import com.example.project_sem_4.model.request.CreateUserReq;
import com.example.project_sem_4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(CreateUserReq req) {
        // Check email exist
        User user = userRepository.findByEmail(req.getEmail());
        if (user != null) {
            throw new DuplicateRecordException("Email is already in use");
        }

        user = UserMapper.toUser(req);
        userRepository.save(user);

        return UserMapper.toUserDto(user);
    }
}
