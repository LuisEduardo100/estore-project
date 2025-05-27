package com.estore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estore.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    Optional<User> findByEmail(String email);
}
