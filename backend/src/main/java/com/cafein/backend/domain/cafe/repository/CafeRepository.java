package com.cafein.backend.domain.cafe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafein.backend.api.home.dto.CafeProjection;
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
		+ "CAST(COALESCE(avg_congestion.avg_congestion, 0) AS UNSIGNED) AS 'averageCongestion' "
		+ "FROM Cafe c "
		+ "LEFT JOIN "
		+ "(SELECT cafe_id, COUNT(*) AS review_count FROM Review GROUP BY cafe_id) r "
		+ "ON c.cafe_id = r.cafe_id "
		+ "LEFT JOIN (SELECT cafe_id, COUNT(*) AS comment_count FROM Comment GROUP BY cafe_id) co "
		+ "ON c.cafe_id = co.cafe_id "
		+ "LEFT JOIN opening_hour oh "
		+ "ON c.cafe_id = oh.cafe_id AND DATE_FORMAT(CURRENT_DATE(), '%W') = oh.day_of_week "
		+ "LEFT JOIN "
		+ "(SELECT cafe_id, AVG( CASE cafe_congestion WHEN 'LOW' THEN 1 WHEN 'MEDIUM' THEN 2 WHEN 'HIGH' THEN 3 END) AS avg_congestion "
		+ "FROM Review WHERE created_time >= DATE_SUB(NOW(), INTERVAL 1 HOUR) GROUP BY cafe_id) "
		+ "avg_congestion ON c.cafe_id = avg_congestion.cafe_id ORDER BY "
		+ "(COALESCE(r.review_count, 0) + COALESCE(co.comment_count, 0)) DESC, c.name ", nativeQuery = true)
	List<CafeProjection> getHomeData();

	Optional<Cafe> findByName(String name);

	List<Cafe> findAllByLocal(Local local);

	long count();
}
