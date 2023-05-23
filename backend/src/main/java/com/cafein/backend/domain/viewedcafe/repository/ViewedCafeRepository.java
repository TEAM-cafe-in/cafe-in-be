package com.cafein.backend.domain.viewedcafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafein.backend.domain.viewedcafe.entity.ViewedCafe;

public interface ViewedCafeRepository extends JpaRepository<ViewedCafe, Long> {
}
