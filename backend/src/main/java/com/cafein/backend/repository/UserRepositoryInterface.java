package com.cafein.backend.repository;

import java.util.Optional;

import com.cafein.backend.domain.User;

public interface UserRepositoryInterface {

	Optional<User> findByEmail(String email);
}
