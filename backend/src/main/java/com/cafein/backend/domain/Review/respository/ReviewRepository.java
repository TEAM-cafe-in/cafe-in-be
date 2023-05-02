package com.cafein.backend.domain.Review.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafein.backend.domain.Review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
