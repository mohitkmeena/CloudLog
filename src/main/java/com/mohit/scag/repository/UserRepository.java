package com.mohit.scag.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mohit.scag.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}

