package com.cafein.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafein.backend.domain.User;

public interface UserRepository extends JpaRepository<Long, User> {
}
