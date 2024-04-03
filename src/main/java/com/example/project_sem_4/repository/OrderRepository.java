package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.Order;
import com.example.project_sem_4.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select o from Order o join o.users u join o.restaurant r " +
            "WHERE (:id is null OR o.id in (:id)) " +
            "and (:userId is null OR u.id in (:userId)) " +
            "and (:restaurantId is null OR r.id in (:restaurantId)) " +
            "and (:status is null or o.status in (:status)) " +
            "ORDER BY o.id DESC")
    Page<Order> findOrders(Pageable pageable, Long id, Long userId, Long restaurantId, Integer status);

    @Query(value = "SELECT o FROM Order o JOIN o.restaurant r " +
            "WHERE (:restaurantId IS NULL OR r.id = :restaurantId) " +
            "AND (:restaurantStatus IS NULL OR r.status = :restaurantStatus) " +
            "AND (:status IS NULL OR o.status = :status)")
    List<Order> findAllByRestaurant(Long restaurantId, Integer restaurantStatus, Integer status);


    @Query(value = "select o from Order o join o.users u " +
            "where (:userId is null or u.id in (:userId)) " +
            "ORDER BY o.id DESC")
    Page<Order> findAllByUserId(Pageable pageable, Long userId);

    @Query("SELECT MONTH(o.createDate) AS month, SUM(o.totalMoney) AS revenue " +
            "FROM Order o JOIN o.restaurant r " +
            "WHERE YEAR(o.createDate) = YEAR(CURRENT_DATE) " +
            "AND (:restaurantId IS NULL OR r.id = :restaurantId) " +
            "AND (:status IS NULL OR o.status = :status) " +
            "GROUP BY MONTH(o.createDate)")
    List<Object[]> getTotalRevenueByMonth(Long restaurantId, Integer status);
}
