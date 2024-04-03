package com.example.project_sem_4.controller.admin;

import com.example.project_sem_4.config.JwtTokenUtil;
import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.UserDTO;
import com.example.project_sem_4.model.req.UserReq;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;


@Tag(name = "Admin User", description = "Admin User management APIs")
@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> createUser(@RequestBody UserReq req) {
        UserDTO result = userService.createUser(req);
        if (result == null) return new ResponseEntity<>("User is not created, try again later", HttpStatus.BAD_REQUEST);
        // Gửi mail đến user khi tạo tài khoản thành công
        String subject = "Tài khoản của bạn đã được tạo";
        String body = "Chào " + req.getName() + ", tài khoản của bạn đã được tạo thành công.";
        mailService.sendMailUser(fromEmail, req.getEmail(), subject, body);
        //
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        Pageable pageable = PageRequest.of(0,20);
        Page<UserDTO> users = userService.getAllUsers(pageable);
        DataRes res = new DataRes();
        res.setData(users.getContent());
        res.setPagination(new Pagination(users.getPageable().getPageNumber(), users.getSize(), users.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @PostMapping("/list")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> getUsers(@RequestBody UserReq req, Authentication authentication) throws ParseException {
        User user = userService.findByEmail(authentication.getName());
        Long restaurantId = null;
        if (user.getRestaurant() != null) {
            restaurantId = user.getRestaurant().getId();
        }
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<UserDTO> page = userService.getUsers(
                pageable,
                req.getId(),
                req.getName(),
                req.getEmail(),
                req.getTel(),
                req.getAddress(),
                req.getBirthday(),
                req.getType(),
                restaurantId,
                req.getStatus()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('SHIPPER')")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        UserDTO res = userService.getProfile(user.getId());

        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('SHIPPER')")
    public ResponseEntity<?> updateUser(@RequestBody UserReq req, @PathVariable Long id) throws IOException {
        UserDTO result = userService.updateUser(req, id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/avatar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('SHIPPER')")
    public ResponseEntity<?> updateAvatar(@ModelAttribute UserReq req, @PathVariable Long id) throws IOException {
        UserDTO result = userService.updateAvatar(req, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Delete Success");
    }
}
