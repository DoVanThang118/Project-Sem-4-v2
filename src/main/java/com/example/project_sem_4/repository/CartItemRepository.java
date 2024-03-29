package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.CartItem;
import com.example.project_sem_4.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByProductId(Long id);

    @Query(value = "select c from CartItem c join c.product p join c.user u " +
            "where (:userId is null or u.id in (:userId)) " +
            "and (:productId is null or p.id in (:productId)) "
    )
    CartItem findByProductIdAndUserId(Long productId, Long userId);
}
