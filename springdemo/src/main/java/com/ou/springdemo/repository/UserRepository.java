package com.ou.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ou.springdemo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    //Tự động có những method CRUD
}
