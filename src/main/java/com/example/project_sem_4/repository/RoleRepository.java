package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Query(value = "select r from Role r " +
            "where (:name is null or r.name like %:name%) " +
            "and (:description is null or r.description like %:description%)" +
            "and (:status is null or r.status in (:status))"
    )
    Page<Role> findRoles(Pageable pageable, String name, String description, Integer status);
}
