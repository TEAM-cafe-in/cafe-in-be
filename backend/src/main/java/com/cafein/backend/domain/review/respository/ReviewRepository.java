package com.cafein.backend.domain.review.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cafein.backend.api.member.dto.MemberReviewProjection;
import com.cafein.backend.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query(value = "SELECT "
		+ "c.name AS 'cafeName', "
		+ "CONCAT(c.sigungu, ' ', c.road_name, c.house_number) AS 'address', "
		+ "r.cafe_congestion AS 'cafeCongestion', "
		+ "r.is_clean AS 'isClean', "
		+ "r.has_plug AS 'hasPlug' "
		+ "FROM Review r "
		+ "LEFT JOIN "
		+ "Cafe c ON c.cafe_id = r.cafe_id "
		+ "WHERE r.member_id = :memberId ", nativeQuery = true)
	List<MemberReviewProjection> findReviewsByMemberId(@Param("memberId") Long memberId);

	@Query("select count(r) from Review r where r.member.memberId = :memberId")
	long countReviewByMemberId(@Param("memberId") Long memberId);

	@Query(value = "SELECT "
		+ "r.cafe_id "
		+ "FROM Review r "
		+ "WHERE r.member_id = :memberId "
		+ "AND r.created_time > DATE_SUB(NOW(), INTERVAL 1 DAY) ", nativeQuery = true)
	List<Long> findCafeIdsOfRecentReviews(@Param("memberId") Long memberId);
}
