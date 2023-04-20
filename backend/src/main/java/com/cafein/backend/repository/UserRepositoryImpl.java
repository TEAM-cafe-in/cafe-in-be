package com.cafein.backend.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Repository;

import com.cafein.backend.domain.User;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryInterface {

	@Override
	public Optional<User> findByEmail(final String email) {
		Optional<User> user = findByEmail(email);
		return user;
	}
}
