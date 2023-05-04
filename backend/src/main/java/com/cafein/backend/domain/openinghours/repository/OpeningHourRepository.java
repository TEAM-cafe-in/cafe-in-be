package com.cafein.backend.domain.openinghours.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafein.backend.domain.openinghours.entity.OpeningHour;

public interface OpeningHourRepository extends JpaRepository<OpeningHour, Long> {
}
