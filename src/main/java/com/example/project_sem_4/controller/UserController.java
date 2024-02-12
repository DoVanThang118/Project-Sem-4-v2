package com.example.project_sem_4.controller;

import com.example.project_sem_4.model.dto.UserDTO;
import com.example.project_sem_4.model.req.AuthReq;
import com.example.project_sem_4.model.req.UserReq;
import com.example.project_sem_4.model.res.AuthRes;
import com.example.project_sem_4.config.JwtTokenUtil;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.service.MailService;
import com.example.project_sem_4.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.text.ParseException;
import java.util.List;

@Tag(name = "User", description = "User management APIs")
@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @PostMapping("/register")
    public ResponseEntity<?> createUser( @RequestBody UserReq req) {
        UserDTO result = userService.createUser(req);
        if (result == null) return new ResponseEntity<>("User is not created, try again later", HttpStatus.BAD_REQUEST);
        // Gửi mail đến user khi tạo tài khoản thành công
        String subject = "Tài khoản của bạn đã được tạo";
        String body = "Chào " + req.getName() + ", tài khoản của bạn đã được tạo thành công.";
        mailService.sendMailUser(fromEmail, req.getEmail(), subject, body);
        //
        return ResponseEntity.ok(result);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@Valid @RequestBody AuthReq req) {
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getEmail(),
                        req.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Gen token
        String jwt = jwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());

        return ResponseEntity.ok(new AuthRes(jwt));
    }

    @GetMapping("/api/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUser() {
        List<UserDTO> users = userService.getUser();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<?> findId(@PathVariable Long id) {
        UserDTO result = userService.findId(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/users/search")
    public ResponseEntity<?> searchUser(@RequestParam(value = "keyword", required = false, defaultValue = "") String name) {
        List<UserDTO> users = userService.searchUser(name);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/api/users/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsers(@RequestBody UserReq req) throws ParseException {
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<UserDTO> page = userService.getUsers(
                pageable,
                req.getName(),
                req.getEmail(),
                req.getTel(),
                req.getAddress(),
                req.getBirthday(),
                req.getType(),
                req.getStatus()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserReq req, @PathVariable Long id) {
        UserDTO result = userService.updateUser(req, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Delete Success");
    }
}