package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String name);

//    @Query(value = "select r from Restaurant r " +
//            "WHERE (:id is null or :id = '' or r.id in (:id)) " +
//            "and (:name is null or :name = '' or r.name like %:name%) " +
//            "and (:description is null or :description = '' or r.description like %:description%) " +
//            "and (:tel is null or :tel = '' or r.tel like %:tel%) " +
//            "and (:address is null or :address = '' or r.address like %:address%) " +
//            "and (:status is null or :status = '' or r.status in (:status)) "
//    )
    @Query(value = "select r from Restaurant r " +
            "where (coalesce(:id, null) is null or :id = '' or r.id in (:id)) " +
            "and (coalesce(:name, null) is null or :name = '' or r.name like %:name%) " +
            "and (coalesce(:description, null) is null or :description = '' or r.description like %:description%) " +
            "and (coalesce(:tel, null) is null or :tel = '' or r.tel like %:tel%) " +
            "and (coalesce(:address, null) is null or :address = '' or r.address like %:address%) " +
            "and (coalesce(:status, null) is null or :status = '' or r.status in (:status)) "
    )
    Page<Restaurant> findRestaurants(
            Pageable pageable,
            @Param("id") Long id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("tel") String tel,
            @Param("address") String address,
            @Param("status") Integer status
    );
}
