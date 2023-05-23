package com.cafein.backend.domain.cafe.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cafein.backend.domain.cafe.entity.Cafe;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

	Optional<Cafe> findByName(String name);
}
