package com.example.project_sem_4.model.dto;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.entity.Image;
import com.example.project_sem_4.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String tel;
    private String address;
    private Date birthday;
    private String type;
    private Integer status;
//    private List<CartDTO> carts;
    private Set<RoleDTO> roles;
    private Set<ImageDTO> images;
}
