package com.example.project_sem_4.controller;

import com.example.project_sem_4.entity.Restaurant;
import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.OrderDTO;
import com.example.project_sem_4.model.req.OrderReq;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.repository.RestaurantRepository;
import com.example.project_sem_4.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "Order management APIs")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Value("${spring.mail.send.url.client.order}")
    private String url;


    @PostMapping("/create")
    @Transactional
    public ResponseEntity<?> createOrder(@RequestBody OrderReq req, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        OrderDTO create = orderService.saveOrder(req, user);
        // Gửi mail đến user khi tạo tài khoản thành công
        String subject = "Đơn hàng của bạn đã được tạo";
        String body = String.format("Chào %s, Đơn hàng của bạn đã được tạo thành công. Vui lòng truy cập vào %s để xem chi tiết.", user.getName(), url);

        mailService.sendMailUser(user.getEmail(), null, subject, body);
        //
        return ResponseEntity.ok(create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@RequestBody OrderReq req, @PathVariable Long id, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        OrderDTO update = orderService.updateOrder(req, id, user);

        // Gửi mail đến user khi tạo tài khoản thành công
        String subject = "Đơn hàng của bạn đã được cập nhật";
        String status = switch (req.getStatus()) {
            case 1 -> "Đang chờ xác nhận đơn hàng, đang chờ thanh toán";
            case 2 -> "Đang chờ xác nhận đơn hàng, đã thanh toán";
            case 3 -> "Đã xác nhận đơn hàng, đang chờ thanh toán";
            case 4 -> "Đã xác nhận đơn hàng, đã thanh toán";
            case 5 -> "Đang giao hàng";
            case 6 -> "Hoàn thành";
            case 0 -> "Đã hủy";
            default -> "Không xác định";
        };
        String body = String.format("Chào %s, Đơn hàng của bạn đã được cập nhật thành công. Trạng thái hiện tại: %s. Vui lòng truy cập vào %s để xem chi tiết.", user.getName(), status, url);

        mailService.sendMailUser(user.getEmail(), null, subject, body);
        //
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Delete Success");
    }

    @PostMapping("/list")
    public ResponseEntity<?> getOrders(@RequestBody OrderReq req, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Long restaurantId = null;
        if (user.getRestaurant() != null) {
            restaurantId = user.getRestaurant().getId();
        }
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<OrderDTO> page = orderService.getOrders(
                pageable,
                req.getId(),
                req.getUserId(),
                restaurantId,
                req.getStatus()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllOrders(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Pageable pageable = PageRequest.of(0,20);
        Page<OrderDTO> orders = orderService.getAllOrders(pageable, user.getId());
        DataRes res = new DataRes();
        res.setData(orders.getContent());
        res.setPagination(new Pagination(orders.getPageable().getPageNumber(), orders.getSize(), orders.getTotalElements()));
        return ResponseEntity.ok(res);
    }
}
