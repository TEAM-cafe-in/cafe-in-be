package com.cafein.backend.global.config;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.domain.Review.constant.CafeCongestion;
import com.cafein.backend.domain.Review.entity.Review;
import com.cafein.backend.domain.Review.respository.ReviewRepository;
import com.cafein.backend.domain.cafe.constant.Local;
import com.cafein.backend.domain.cafe.entity.Cafe;
import com.cafein.backend.domain.cafe.repository.CafeRepository;
import com.cafein.backend.domain.comment.entity.Comment;
import com.cafein.backend.domain.comment.repository.CommentRepository;
import com.cafein.backend.domain.common.Address;
import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.constant.Role;
import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.repository.MemberRepository;
import com.cafein.backend.domain.openinghours.entity.OpeningHour;
import com.cafein.backend.domain.openinghours.repository.OpeningHourRepository;

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
		private final ReviewRepository reviewRepository;
		private final CommentRepository commentRepository;
		private final OpeningHourRepository openingHourRepository;

		public void dbInit() {
			Review review = Review.builder()
				.cafeCongestion(CafeCongestion.HIGH)
				.isClean(true)
				.hasPlug(true)
				.build();
			reviewRepository.save(review);
			ArrayList<Review> reviews = new ArrayList<>();
			reviews.add(review);

			OpeningHour openingHour = OpeningHour.builder()
				.dayOfWeek(DayOfWeek.MONDAY)
				.openTime(LocalTime.now())
				.closeTime(LocalTime.MIDNIGHT)
				.build();
			ArrayList<OpeningHour> openingHours = new ArrayList<>();
			openingHourRepository.save(openingHour);
			openingHours.add(openingHour);

			Comment comment = Comment.builder()
				.content("hello")
				.build();
			ArrayList<Comment> comments = new ArrayList<>();
			commentRepository.save(comment);
			comments.add(comment);

			Cafe cafe1 = Cafe.builder()
				.name("브레디포스트 성수")
				.info("[BREADYPOST FLAGSHIP STORE SEONGSU]")
				.local(Local.SEONGSU)
				.address(
					createAddress("서울", "서울시 성동구", "상원1길", "5 1층")
				)
				.openingHours(openingHours)
				.reviews(reviews)
				.comments(comments)
				.build();
			try {
				cafeRepository.save(cafe1);
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
