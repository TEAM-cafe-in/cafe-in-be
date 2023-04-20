package com.cafein.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafein.backend.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryInterface {
}
