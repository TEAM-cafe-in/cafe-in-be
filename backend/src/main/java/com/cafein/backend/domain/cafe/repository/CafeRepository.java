package com.cafein.backend.domain.cafe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafein.backend.domain.cafe.constant.Local;
import com.cafein.backend.domain.cafe.entity.Cafe;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

	Optional<Cafe> findByName(String name);
	List<Cafe> findAllByLocal(Local local);
	int countByLocal(Local local);
}
