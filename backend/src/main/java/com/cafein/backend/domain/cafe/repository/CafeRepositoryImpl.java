package com.cafein.backend.domain.cafe.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.cafein.backend.api.home.dto.HomeDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CafeRepositoryImpl {

	private final EntityManager em;

	public List<HomeDTO.Response> findHomeDTO() {
		return em.createQuery(
			"SELECT c.name, CONCAT(c.sigungu, ' ', c.road_name, c.house_number), " +
				"(COALESCE(r.reviewCount, 0) + COALESCE(co.commentCount, 0)), " +
				"CASE WHEN CURRENT_TIME() BETWEEN oh.openTime AND oh.closeTime THEN '영업중' ELSE '영업종료' END, " +
				"CAST(COALESCE(avgCong.avgCongestion, 0) AS int) " +
				"FROM Cafe c " +
				"LEFT JOIN (SELECT r.cafe.id, COUNT(r.id) AS reviewCount FROM Review r GROUP BY r.cafe.id) r " +
				"ON c.id = r.cafe.id " +
				"LEFT JOIN (SELECT co.cafe.id, COUNT(co.id) AS commentCount FROM Comment co GROUP BY co.cafe.id) co " +
				"ON c.id = co.cafe.id " +
				"LEFT JOIN OpeningHour oh ON c.id = oh.cafe.id AND FUNCTION('DAYNAME', CURRENT_DATE) = oh.dayOfWeek " +
				"LEFT JOIN (SELECT r.cafe.id, AVG(CASE r.cafeCongestion " +
				"WHEN 'LOW' THEN 1 WHEN 'MEDIUM' THEN 2 WHEN 'HIGH' THEN 3 END) AS avgCongestion " +
				"FROM Review r WHERE r.createdTime >= FUNCTION('DATE_SUB', CURRENT_TIMESTAMP, 1, 'HOUR') GROUP BY r.cafe.id) avgCong " +
				"ON c.id = avgCong.cafe.id " +
				"WHERE c.local = 'SEONGSU' " +
				"ORDER BY (COALESCE(r.reviewCount, 0) + COALESCE(co.commentCount, 0)) DESC, c.name", HomeDTO.Response.class
		).getResultList();
	}
}
