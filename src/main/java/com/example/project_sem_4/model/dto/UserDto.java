package com.example.project_sem_4.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int id;

    private String name;

    private String email;

    private String phone;

    private String avatar;

    private Date birthday;
}
