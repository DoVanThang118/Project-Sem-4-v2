package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String name);

    @Query(value = "select r from Restaurant r " +
            "where (:id is null or r.id = :id) " +
            "and (:name is null or r.name like %:name%) " +
            "and (:description is null or r.description like %:description%) " +
            "and (:tel is null or r.tel like %:tel%) " +
            "and (:address is null or r.address like %:address%) " +
            "and (:meals is null or :meals member of r.meals) " +
            "and (:cuisines is null or :cuisines member of r.cuisines) " +
            "and (:hourStart is null or r.hourStart = :hourStart) " +
            "and (:hourEnd is null or r.hourEnd = :hourEnd) " +
            "and (:rate is null or r.rate in :rate) " +
            "and (:status is null or r.status in :status)"
    )
//    @Query(value = "select r from Restaurant r " +
//            "where (coalesce(:id, null) is null or :id = '' or r.id in (:id)) " +
//            "and (coalesce(:name, null) is null or :name = '' or r.name like %:name%) " +
//            "and (coalesce(:description, null) is null or :description = '' or r.description like %:description%) " +
//            "and (coalesce(:tel, null) is null or :tel = '' or r.tel like %:tel%) " +
//            "and (coalesce(:address, null) is null or :address = '' or r.address like %:address%) " +
//            "and (coalesce(:meals, null) is null or :meals = '' or r.meals in (:meals)) " +
//            "and (coalesce(:cuisines, null) is null or :cuisines = '' or r.cuisines in (:cuisines)) " +
//            "and (coalesce(:hourStart, null) is null or :hourStart = '' or r.hourStart = :hourStart) " +
//            "and (coalesce(:hourEnd, null) is null or :hourEnd = '' or r.hourEnd = :hourEnd) " +
//            "and (coalesce(:rate, null) is null or :rate = '' or r.rate in (:rate)) " +
//            "and (coalesce(:status, null) is null or :status = '' or r.status in (:status)) "
//    )
    Page<Restaurant> findRestaurants(
            Pageable pageable,
            @Param("id") Long id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("tel") String tel,
            @Param("address") String address,
            @Param("meals") List<String> meals,
            @Param("cuisines") List<String> cuisines,
            @Param("hourStart") LocalTime hourStart,
            @Param("hourEnd") LocalTime hourEnd,
            @Param("rate") Double rate,
            @Param("status") Integer status
    );
}
