package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String name);

    @Query(value = "select r from Restaurant r " +
            "WHERE (:id is null OR r.id in (:id)) " +
            "and (:name is null or r.name like %:name%) " +
            "and (:description is null or r.description like %:description%) " +
            "and (:tel is null or r.tel like %:tel%) " +
            "and (:address is null or r.address like %:address%) " +
            "and (:status is null or r.status in (:status)) "
    )
    Page<Restaurant> findRestaurants(Pageable pageable, Long id, String name, String description, String tel, String address, Integer status);
}
