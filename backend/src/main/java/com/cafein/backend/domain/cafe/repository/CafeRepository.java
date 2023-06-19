package com.cafein.backend.domain.cafe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cafein.backend.api.cafe.dto.CafeInfoProjection;
import com.cafein.backend.api.home.dto.HomeProjection;
import com.cafein.backend.domain.cafe.constant.Local;
import com.cafein.backend.domain.cafe.entity.Cafe;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

	@Query(value = "SELECT "
		+ "c.cafe_id AS 'cafeId', "
		+ "c.name AS 'name', "
		+ "c.local AS 'local', "
		+ "c.latitude AS 'latitude', "
		+ "c.longitude AS 'longitude', "
		+ "COALESCE(c.phone_number, '등록된 전화번호가 없습니다') AS 'phoneNumber', "
		+ "CONCAT(c.sigungu, ' ', c.road_name, c.house_number) AS 'address', "
		+ "(COALESCE(r.review_count, 0) + COALESCE(co.comment_count, 0)) AS 'commentReviewCount', "
		+ "CASE WHEN CURRENT_TIME() BETWEEN oh.open_time AND oh.close_time THEN '영업중' ELSE '영업종료' END AS 'status', "
		+ "CASE WHEN vc.cafe_id IS NOT NULL THEN CAST(COALESCE(avg_congestion.avg_congestion, 0) AS UNSIGNED) ELSE '실시간 혼잡도 알아보기' END AS 'averageCongestion'"
		+ "FROM Cafe c "
		+ "LEFT JOIN "
		+ "(SELECT cafe_id, COUNT(*) AS review_count FROM Review GROUP BY cafe_id) r "
		+ "ON c.cafe_id = r.cafe_id "
		+ "LEFT JOIN (SELECT cafe_id, COUNT(*) AS comment_count FROM Comment GROUP BY cafe_id) co "
		+ "ON c.cafe_id = co.cafe_id "
		+ "LEFT JOIN opening_hour oh "
		+ "ON c.cafe_id = oh.cafe_id AND DATE_FORMAT(CURRENT_DATE(), '%W') = oh.day_of_week "
		+ "LEFT JOIN "
		+ "(SELECT cafe_id, AVG(CASE cafe_congestion WHEN 'LOW' THEN 1 WHEN 'MEDIUM' THEN 2 WHEN 'HIGH' THEN 3 END) AS avg_congestion "
		+ "FROM Review WHERE created_time >= DATE_SUB(NOW(), INTERVAL 1 HOUR) GROUP BY cafe_id) "
		+ "avg_congestion ON c.cafe_id = avg_congestion.cafe_id "
		+ "LEFT JOIN "
		+ "viewed_cafe vc ON c.cafe_id = vc.cafe_id AND vc.member_id = :memberId "
		+ "ORDER BY "
		+ "(COALESCE(r.review_count, 0) + COALESCE(co.comment_count, 0)) DESC, c.name ", nativeQuery = true)
	List<HomeProjection> getHomeData(@Param("memberId") Long memberId);

	@Query(value = "SELECT "
		+ "c.cafe_id AS 'cafeId', "
		+ "c.name AS 'name', "
		+ "c.local AS 'local', "
		+ "c.latitude AS 'latitude', "
		+ "c.longitude AS 'longitude', "
		+ "(SELECT COUNT(has_plug) FROM review r WHERE c.cafe_id = r.cafe_id AND r.has_plug=1) AS 'hasPlugCount', "
		+ "(SELECT COUNT(is_clean) FROM review r WHERE c.cafe_id = r.cafe_id AND r.is_clean=1) AS 'isCleanCount', "
		+ "COALESCE(c.phone_number, '등록된 전화번호가 없습니다') AS 'phoneNumber', "
		+ "CONCAT(c.sigungu, ' ', c.road_name, c.house_number) AS 'address', "
		+ "CASE WHEN CURRENT_TIME() BETWEEN oh.open_time AND oh.close_time THEN '영업중' ELSE '영업종료' END AS 'status', "
		+ "CASE WHEN vc.cafe_id IS NOT NULL THEN CAST(COALESCE(avg_congestion.avg_congestion, 0) AS UNSIGNED) ELSE '실시간 혼잡도 알아보기' END AS 'averageCongestion'"
		+ "FROM Cafe c "
		+ "LEFT JOIN "
		+ "(SELECT cafe_id, COUNT(*) AS review_count FROM Review GROUP BY cafe_id) r "
		+ "ON c.cafe_id = r.cafe_id "
		+ "LEFT JOIN (SELECT cafe_id, COUNT(*) AS comment_count FROM Comment GROUP BY cafe_id) co "
		+ "ON c.cafe_id = co.cafe_id "
		+ "LEFT JOIN opening_hour oh "
		+ "ON c.cafe_id = oh.cafe_id AND DATE_FORMAT(CURRENT_DATE(), '%W') = oh.day_of_week "
		+ "LEFT JOIN "
		+ "(SELECT cafe_id, AVG(CASE cafe_congestion WHEN 'LOW' THEN 1 WHEN 'MEDIUM' THEN 2 WHEN 'HIGH' THEN 3 END) AS avg_congestion "
		+ "FROM Review WHERE created_time >= DATE_SUB(NOW(), INTERVAL 1 HOUR) GROUP BY cafe_id) "
		+ "avg_congestion ON c.cafe_id = avg_congestion.cafe_id "
		+ "LEFT JOIN "
		+ "viewed_cafe vc ON c.cafe_id = vc.cafe_id AND vc.member_id = :memberId "
		+ "WHERE c.cafe_id = :cafeId "
		+ "ORDER BY "
		+ "(COALESCE(r.review_count, 0) + COALESCE(co.comment_count, 0)) DESC, c.name ", nativeQuery = true)
	CafeInfoProjection findCafeInfoById(@Param("memberId") Long memberId, @Param("cafeId") Long cafeId);

	@Query("SELECT co.content FROM Comment co WHERE co.cafe.cafeId = :cafeId")
	List<String> findCommentsByCafeId(@Param("cafeId") Long cafeId);

	Optional<Cafe> findByName(String name);

	List<Cafe> findAllByLocal(Local local);

	long count();
}
