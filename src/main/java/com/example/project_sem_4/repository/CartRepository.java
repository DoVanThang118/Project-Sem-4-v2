package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.Cart;
import com.example.project_sem_4.entity.Restaurant;
import com.example.project_sem_4.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    //    @Query(value = "select c from Cart c " +
//            "where (coalesce(:status, null) is null or c.status in (:status)) ")
    @Query(value = "select c from Cart c join c.user u join c.restaurant r " +
            "WHERE (:id is null OR c.id in (:id)) " +
            "and (:status is null or c.status in (:status)) " +
            "and (:restaurantId is null or r.id in (:restaurantId)) " +
            "and (:userId is null or u.id in (:userId)) "
    )
    Page<Cart> findCarts(Pageable pageable, Long id, Integer status, Long restaurantId, Long userId);

    List<Cart> findByUser(User user);

    @Query(value = "select c from Cart c join c.user u " +
            "where (:userId is null or u.id in (:userId))")
    Page<Cart> getCarts(Pageable pageable, Long userId);

    @Modifying
    @Query("DELETE FROM Cart c WHERE :userId IS NULL OR c.user.id = :userId")
    void deleteAllByUserId(Long userId);

    Cart findByRestaurantId(Long restaurantId);

    @Query(value = "select c from Cart c join c.restaurant r join c.user u " +
            "where (:userId is null or u.id in (:userId)) " +
            "and (:restaurantId is null or r.id in (:restaurantId)) "
    )
    Cart findByRestaurantIdAndUserId(Long restaurantId, Long userId);


    @Query(value = "select c from Cart c join c.user u " +
            "where (:userId is null or u.id in (:userId)) " +
            "and (:id is null or c.id in (:id)) "
    )
    Optional<Cart> findByIdAndUserId(Long id, Long userId);
}
