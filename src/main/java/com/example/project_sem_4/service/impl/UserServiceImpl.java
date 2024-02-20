package com.example.project_sem_4.service.impl;

import com.example.project_sem_4.entity.Role;
import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.UserDTO;
import com.example.project_sem_4.model.mapper.UserMapper;
import com.example.project_sem_4.model.mapper.UserPageMapper;
import com.example.project_sem_4.model.req.UserReq;
import com.example.project_sem_4.repository.RoleRepository;
import com.example.project_sem_4.repository.UserRepository;
import com.example.project_sem_4.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;


    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        Role role = roleRepository.findByName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        if (req.getType() != null) {
            role = roleRepository.findByName(req.getType().toUpperCase(Locale.ROOT));
            roles.add(role);
            req.setRoles(roles);
        }
        user = UserMapper.toUser(req);
        user.setStatus(1);
        userRepository.save(user);

        return UserMapper.userDto(user);
    }

    @Override
    public UserDTO updateUser(UserReq req, Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("Not Found User");
        }

        User update = UserMapper.toUser(req, id);
        try {
            userRepository.save(update);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Database error. Can't update user");
        }

        return UserMapper.userDto(update);
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
    public Page<UserDTO> getUsers(Pageable pageable, Long id, String name, String email, String tel, String address, Date birthday, String type, Integer status) {
        Page<User> users = userRepository.findUsers(pageable, id, name, email, tel ,address, birthday, type, status);
        return users.map(UserPageMapper.INSTANCE::mapEntityToDTO);
    }
}
