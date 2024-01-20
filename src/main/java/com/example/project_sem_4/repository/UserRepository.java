package com.example.project_sem_4.repository;

import com.example.project_sem_4.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    @Query(value = "select u from User u " +
            "where (coalesce(:name, null) is null or u.name like %:name%) " +
            "and (coalesce(:email, null) is null or u.email like %:email%) " +
            "and (coalesce(:tel, null) is null or u.tel like %:tel%) " +
            "and (coalesce(:address, null) is null or u.address like %:address%) " +
            "and (coalesce(:birthday, null) is null or u.birthday in (:birthday)) " +
            "and (coalesce(:type, null) is null or u.type like %:type%) " +
            "and (coalesce(:status, null) is null or u.status in (:status)) "
    )
    Page<User> findUsers(Pageable pageable, String name, String email, String tel, String address, Date birthday, String type, Integer status);
}
