package com.example.project_sem_4.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthday;
    private String type;
    private Integer status;
//    private List<CartDTO> carts;
    private Set<RoleDTO> roles;
    private Set<ImageDTO> images;
}
