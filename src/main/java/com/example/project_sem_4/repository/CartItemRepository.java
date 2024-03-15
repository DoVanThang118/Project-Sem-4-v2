package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.CartItem;
import com.example.project_sem_4.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByProductId(Long id);

}
