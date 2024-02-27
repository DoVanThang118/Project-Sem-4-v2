package com.example.project_sem_4.model.mapper;

import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.UserDTO;
import com.example.project_sem_4.model.req.UserReq;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static UserDTO userDto(User user) {
//        UserDTO tmp = new UserDTO();
//        tmp.setId(user.getId());
//        tmp.setName(user.getName());
//        tmp.setEmail(user.getEmail());
//        tmp.setTel(user.getTel());
//        tmp.setAddress(user.getAddress());
//        tmp.setBirthday(user.getBirthday());
//        tmp.setType(user.getType());
//        tmp.setCarts(user.getCarts());
//        tmp.setStatus(user.getStatus());
//        tmp.setRoles(user.getRoles());
//        tmp.setImages(user.getImages());
//
//        return tmp;
        return modelMapper.map(user, UserDTO.class);
    }

    public static User toUser(UserReq req) {
//        User user = new User();
//        user.setName(req.getName());
//        user.setEmail(req.getEmail());
//        user.setTel(req.getTel());
//        user.setAddress(req.getAddress());
//        user.setBirthday(req.getBirthday());
//        user.setType(req.getType());
//        user.setStatus(req.getStatus());
//        user.setCarts(req.getCarts());
//        user.setRoles(req.getRoles());
//        user.setImages(req.getImages());
//        // Hash password using BCryptPasswordEncoder
//        user.setPassword(new BCryptPasswordEncoder(12).encode(req.getPassword()));
//
//        return user;
        User user = modelMapper.map(req, User.class);

        // Bảo mật mật khẩu bằng cách hash bằng BCrypt
        String hashedPassword = new BCryptPasswordEncoder(12).encode(user.getPassword());
        user.setPassword(hashedPassword);

        return user;
    }

//    public static User toUser(UserReq req, Long id) {
//        User user = new User();
//        user.setId(id);
//        user.setEmail(req.getEmail());
//        user.setName(req.getName());
//        user.setTel(req.getTel());
//        user.setAddress(req.getAddress());
//        user.setBirthday(req.getBirthday());
//        user.setType(req.getType());
//        user.setStatus(req.getStatus());
//        user.setCarts(req.getCarts());
//        user.setRoles(req.getRoles());
//        user.setImages(req.getImages());
//        // Hash password using BCrypt
//        String hash = BCrypt.hashpw(req.getPassword(), BCrypt.gensalt(12));
//        user.setPassword(hash);
//
//        return user;
//    }
}
