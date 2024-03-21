package com.example.project_sem_4.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface VNPayService {

    public String createOrder(int total, String urlReturn, HttpServletRequest request);

    public int orderReturn(HttpServletRequest request);
}
