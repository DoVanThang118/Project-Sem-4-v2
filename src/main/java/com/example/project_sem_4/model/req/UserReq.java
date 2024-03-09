package com.example.project_sem_4.model.req;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.project_sem_4.entity.Role;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserReq {

    private Long id;

    @NotNull(message = "Name is required")
    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    @Size(min = 4, max = 20, message = "Pasword must be between 4 and 20 characters")
    private String password;

    @NotNull(message = "Telephone is required")
    @NotEmpty(message = "Telephone is required")
    private String tel;

    @NotNull(message = "Address is required")
    @NotEmpty(message = "Address is required")
    private String address;

    @NotNull(message = "Birthday is required")
    @NotEmpty(message = "Birthday is required")
    private LocalDate birthday;

    @NotNull(message = "Type is required")
    @NotEmpty(message = "Type is required")
    private String type;

    private Integer status;

    private List<Cart> carts;

    private Set<Role> roles;

    private Set<Image> images;

    private int pageNumber = 0;

    private int pageSize = 20;
}
