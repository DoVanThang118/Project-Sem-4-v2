package com.example.project_sem_4.controller;

import com.example.project_sem_4.entity.User;
import com.example.project_sem_4.model.dto.BrandDTO;
import com.example.project_sem_4.model.dto.OrderDetailDTO;
import com.example.project_sem_4.model.req.OrderDetailReq;
import com.example.project_sem_4.model.res.DataRes;
import com.example.project_sem_4.model.res.Pagination;
import com.example.project_sem_4.service.OrderDetailService;
import com.example.project_sem_4.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order_details")
@Tag(name = "OrderDetail", description = "OrderDetail management APIs")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrderDetails(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        List<OrderDetailDTO> create = orderDetailService.create(user);
        return ResponseEntity.ok(create);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Long id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok("Delete Success");
    }

    @PostMapping("/list")
    public ResponseEntity<?> getCart(@RequestBody OrderDetailReq req) {
        Pageable pageable = PageRequest.of(req.getPageNumber(), req.getPageSize());
        Page<OrderDetailDTO> page = orderDetailService.getOrderDetails(
                pageable,
                req.getId(),
                req.getStatus()
        );
        DataRes res = new DataRes();
        res.setData(page.getContent());
        res.setPagination(new Pagination(page.getPageable().getPageNumber(), page.getSize(), page.getTotalElements()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllBrands() {
        Pageable pageable = PageRequest.of(0,20);
        Page<OrderDetailDTO> orderDetails = orderDetailService.getAllOrderDetails(pageable);
        DataRes res = new DataRes();
        res.setData(orderDetails.getContent());
        res.setPagination(new Pagination(orderDetails.getPageable().getPageNumber(), orderDetails.getSize(), orderDetails.getTotalElements()));
        return ResponseEntity.ok(res);
    }
}
