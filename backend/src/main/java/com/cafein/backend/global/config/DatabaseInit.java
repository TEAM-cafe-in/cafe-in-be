package com.cafein.backend.global.config;

import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.domain.cafe.constant.Local;
import com.cafein.backend.domain.cafe.entity.Cafe;
import com.cafein.backend.domain.cafe.repository.CafeRepository;
import com.cafein.backend.domain.common.Address;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Profile({"dev"})
@Component
@RequiredArgsConstructor
public class DatabaseInit {

	private final InitService initService;

	@PostConstruct
	public void init() {
		initService.dbInit();
	}

	@Component
	@Transactional
	@RequiredArgsConstructor
	static class InitService {

		private final CafeRepository cafeRepository;
		private final MemberRepository memberRepository;

		public void dbInit() {
			// Cafe cafe1 = Cafe.builder()
			// 	.name("브레디포스트 성수")
			// 	.info("[BREADYPOST FLAGSHIP STORE SEONGSU]")
			// 	.local(Local.SEONGSU)
			// 	.address(
			// 		createAddress("서울", "서울시 성동구", "상원1길", "5 1층")
			// 	)
			// 	.openingHours(new ArrayList<>())
			// 	.reviews(new ArrayList<>())
			// 	.comments(new ArrayList<>())
			// 	.build();
			Member member = new Member(MemberType.KAKAO, "uichan293@naver.com", "a", "a", "a", Role.ADMIN);
			try {
				memberRepository.save(member);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		private Address createAddress(String sido, String sigungu, String roadName, String houseNumber) {
			return Address.builder()
				.sido(sido)
				.sigungu(sigungu)
				.roadName(roadName)
				.houseNumber(houseNumber)
				.build();
		}
	}
}
