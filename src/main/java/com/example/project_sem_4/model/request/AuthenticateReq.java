package com.example.project_sem_4.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticateReq {
//    @NotNull(message = "Email is required")
//    @NotEmpty(message = "Email is required")
//    @Email(message = "Please provide a valid email")
    private String email;

//    @NotNull(message = "Password is required")
//    @NotEmpty(message = "Password is required")
//    @Size(min = 4, max = 20, message = "Password must be between 4 and 20 characters")
    private String password;
}
