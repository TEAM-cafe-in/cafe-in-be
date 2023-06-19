package com.cafein.backend.global.config;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
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
import com.cafein.backend.domain.viewedcafe.entity.ViewedCafe;
import com.cafein.backend.domain.viewedcafe.repository.ViewedCafeRepository;
import com.cafein.backend.global.jwt.dto.JwtTokenDTO;
import com.cafein.backend.global.jwt.service.TokenManager;

import lombok.RequiredArgsConstructor;

@Profile({"dev"})
@Component
@RequiredArgsConstructor
public class DatabaseInitializer {

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
		private final ReviewRepository reviewRepository;
		private final CommentRepository commentRepository;
		private final OpeningHourRepository openingHourRepository;
		private final MemberRepository memberRepository;
		private final ViewedCafeRepository viewedCafeRepository;
		private final TokenManager tokenManager;

		public void dbInit() {
			// Sample 회원 추가

			Member member1 = memberRepository.save(
				Member.builder()
					.memberType(MemberType.KAKAO)
					.email("testuser1@email.com")
					.name("장원준")
					.role(Role.USER)
					.coffeeBean(100)
					.build()
			);

			final JwtTokenDTO jwtTokenDto = tokenManager.createJwtTokenDto(member1.getMemberId(), member1.getRole());
			System.out.println(jwtTokenDto.getAccessToken() + " member1 jwtTokenDto access_token");
			member1.updateRefreshToken(jwtTokenDto);

			Member member2 = memberRepository.save(
				Member.builder()
					.memberType(MemberType.KAKAO)
					.email("testuser2@email.com")
					.name("황의찬")
					.role(Role.USER)
					.coffeeBean(100)
					.build()
			);

			Member member3 = memberRepository.save(
				Member.builder()
					.memberType(MemberType.KAKAO)
					.email("testuser3@email.com")
					.name("유성민")
					.role(Role.USER)
					.coffeeBean(100)
					.build()
			);

			Member member4 = memberRepository.save(
				Member.builder()
					.memberType(MemberType.KAKAO)
					.email("testuser4@email.com")
					.name("강신혁")
					.role(Role.USER)
					.coffeeBean(100)
					.build()
			);

			Member member5 = memberRepository.save(
				Member.builder()
					.memberType(MemberType.KAKAO)
					.email("testuser5@email.com")
					.name("이동훈")
					.role(Role.USER)
					.coffeeBean(100)
					.build()
			);

			viewedCafeRepository.save(
				ViewedCafe.builder()
					.cafeId(1L)
					.member(member1)
					.build()
			);

			viewedCafeRepository.save(
				ViewedCafe.builder()
					.cafeId(2L)
					.member(member1)
					.build()
			);

			viewedCafeRepository.save(
				ViewedCafe.builder()
					.cafeId(3L)
					.member(member1)
					.build()
			);

			// 성수동 카페
			cafeRepository.save(
				Cafe.builder()
					.name("브레디포스트 성수")
					.info("[BREADYPOST FLAGSHIP STORE SEONGSU]")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "상원1길", "5 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			Cafe cafe = cafeRepository.findByName("브레디포스트 성수").get();
			addCommentsToCafe(member1, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("블루보틀 성수")
					.info("블루보틀 성수 카페는 주차 공간이 많이 협소합니다."
					+ "근처 공영주차장 또는 유료 주차장을 이용해 주세요.")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "아차산로", "7 케이티링커스")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("블루보틀 성수").get();
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("서울앵무새")
					.info("그릇에 그림 그리는 성수동 신상 카페")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "서울숲9길", "3 B1~2F")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("서울앵무새").get();
			addCommentsToCafe(member1, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("묘사서울")
					.info("붕어빵과 앙큐브 맛있는 핫한 성수동 카페")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "서울숲2길", "2 2층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("묘사서울").get();
			addCommentsToCafe(member1, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("라프레플루트")
					.info("다양한 과일을 쉽고 간편하게 접할 수 있는 프리미엄 과일가게 라프레플루트입니다:)")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "서울숲2길", "8-8 2층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("라프레플루트").get();
			addCommentsToCafe(member1, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("DYI WORKSHOP")
					.info("다양한 원두 라인업과 다양한 초콜릿의 페어링이 가능한 공간")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "서울숲2길", "15-14 2층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("DYI WORKSHOP").get();
			addCommentsToCafe(member1, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("하프커피 성수")
					.info("서울숲 도넛이 맛있는 하프커피")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "서울숲4길", "12 지하1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("하프커피 성수").get();
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("구욱희씨 서울숲점")
					.info("수제쿠키와 디저트가 가득한 서울숲 루프탑 카페")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "서울숲4길", "12-22 1, 2층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("구욱희씨 서울숲점").get();
			addCommentsToCafe(member1, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("어라운드데이")
					.info("서울숲의 방앗간 같은 공간 카페 어라운드데이입니다."
					+ "도심 속 휴양, 방문해주시는 모든 손님과 반려동물이 함께 편히 쉬다 가실 수 있는 공간입니다.")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "서울숲2길", "24-1")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("어라운드데이").get();
			addCommentsToCafe(member1, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("LOWIDE")
					.info("서울숲 베이커리 카페 LOWIDE")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "서울숲2길", "22-1")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("LOWIDE").get();
			addCommentsToCafe(member1, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("퍼먼트")
					.info("좋은 재료 정직한 공정으로 맛있는 베이커리들을 구워냅니다.")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "서울숲2길", "22-1")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("퍼먼트").get();
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("센터커피 서울숲점")
					.info("서울숲이 보이는 루프탑 카페"
					+ "최고 수준의 생두와 로스팅, 추출을 고집하며 지속 가능한 스페셜티커피 문화 확산에 힘쓰고 있습니다.")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "서울숲2길", "28-11")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("센터커피 서울숲점").get();
			addCommentsToCafe(member1, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("체다앤올리")
					.info("맛있는 딸기우유와 서울숲을 바라보며 즐기는 브런치 맛집")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "서울숲2길", "32-14 갤러리아포레 상가 102동 113호")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("체다앤올리").get();
			addCommentsToCafe(member1, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("5to7")
					.info("수플레가 맛있는 서울숲 카페")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "서울숲2길", "44-13 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("5to7").get();
			addCommentsToCafe(member1, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("밀도 성수점")
					.info("밀도 빵집 성수동 서울숲 귀여운 큐브식빵"
					+ "그날의 온도와 습도를 세심하게 고려하여 매일 매일 맛있는 식빵을 구워냅니다.")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "왕십리로", "96")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("밀도 성수점").get();
			addCommentsToCafe(member1, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("노티드 성수")
					.info("노티드 성수에서는 노티드 도넛과 브레드, 성수에서만 만나볼 수 있는 젤라또가 준비되어있습니다!")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "연무장길", "29-17 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("노티드 성수").get();
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("멜로워 성수 더 플래그쉽")
					.info("성수동카페 멜로워 빵이 정말 맛있는곳")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "성수이로7길", "39")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("멜로워 성수 더 플래그쉽").get();
			addCommentsToCafe(member3, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("도토루커피os")
					.info("20년이 넘은 커피오스1997은 커피와 리얼수제tea, 수제 샌드위치가 최고입니다."
					+ "성수동 카페중 가장 오래된 손가락에 꼽히는 핫플레이스 커피오스.")
					.local(Local.SEONGSU)
					.address(
						createAddress("서울", "서울시 성동구", "서울숲2길", "22-1")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("도토루커피os").get();
			addCommentsToCafe(member4, cafe);
			addOpeningHoursToCafe(cafe);

			// 연남동 카페
			cafeRepository.save(
				Cafe.builder()
					.name("하우에버 연남")
					.info("줄 서서 사가는 테이크아웃 카스테라 맛집 ."
						+ "1~4층 통건물 대형카페.(단체석완비.)"
						+ "매일매일 직접 굽는 신선한 카스테라."
						+ "레인보우크림 맛집.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "연희로1길", "45-6")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("하우에버 연남").get();
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("노티드 연남")
					.info("사람과 사람, 사람과 디저트를 잇는 프리미엄 디저트 카페 노티드입니다."
					+ "*노티드 연남 층별 영업시간 안내"
					+ "연남 2층 (Eat-in) : 12:00 - 21:00"
					+ "연남 지하1층 (To-go) : 11:00 - 21:00")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "월드컵북로6길", "12-13")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("노티드 연남").get();
			addCommentsToCafe(member5, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("터틀힙")
					.info("누적판매 10만개,"
						+ "주문제작 케이크 전문점 터틀힙입니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "연남로1길", "44 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("터틀힙").get();
			addCommentsToCafe(member3, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("버터앤쉘터 연남점")
					.info("시그니처 커피 & 수제 디저트 카페 연남 버터앤쉘터 입니다."
						+ "*반려동물 동반은 야외 좌석만 가능합니다.내용 더보기")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로", "170 102호")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("버터앤쉘터 연남점").get();
			addCommentsToCafe(member4, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("조앤도슨")
					.info("사회가 정해둔 규격이나 틀에 나를 맞추지 않고, 온 마음을 다해 계층을 뛰어넘은 사랑을 한 타이타닉의 Jack Dawson 과 시대가 "
						+ "강요한 여성스러움에 반항하기도 하고 누군가를 위해 자신의 소중한 머리카락을 내어주던 Jo March는 "
						+ "인생을 용감하게 살아나가는 인물들이자 권소희 대표가 가장 좋아하는 영화 속 두 캐릭터이다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로41길", "31")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("조앤도슨").get();
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("누크녹")
					.info("누크녹은 그린란드의 작은 수도 누크라는 지역을 모티브로 시작하는 연남동 작은 카페입니다."
						+ "바쁘고 치열한 일상에서 벗어나 '아무 일도 없었고 그 이후에도 아무 일도 없었으며 아무 일도 일어나지 않았다는'"
						+ "그린란드 사람들의 삶의 방식을 추구하며"
						+ "저희 누크녹에서의 평안한 쉼을 제안합니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로", "190-31 2층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("누크녹").get();
			addCommentsToCafe(member5, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("청수당공명")
					.info("청수당의 다섯번째 걸음, 청수당-공명 입니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로", "152")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("청수당공명").get();
			addCommentsToCafe(member2, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("파이인더샵")
					.info("연남동 한적한 주택가 사이, 특별한 공간입니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로27길", "26")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("파이인더샵").get();
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("코코로카라")
					.info("안녕하세요, 마음을 담은 작은 과자점 코코로카라 입니 다 :)")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "연남로1길", "41")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("코코로카라").get();
			addCommentsToCafe(member2, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("랜디스도넛 연남점")
					.info("아이언맨2에서 스타크가 먹은 랜디스도넛 연남동점")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로", "247")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("랜디스도넛 연남점").get();
			addCommentsToCafe(member3, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("파롤앤랑그")
					.info("네모난 파이가 귀엽고 맛있는 연남동 디저트 카페")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로29안길", "8")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("파롤앤랑그").get();
			addCommentsToCafe(member4, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("에브리데이해피벌스데이")
					.info("매일 생일 같은 연남동 카페")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "연희로", "3층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("에브리데이해피벌스데이").get();
			addCommentsToCafe(member2, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("땡스오트 연남")
					.info("땡스오트가 생각하는 좋은 요거트와 그래놀라는 인위적인 첨가물을 넣지 않고 좋은 원재료를 사용하는 것 입니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로23길", "68")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("땡스오트 연남").get();
			addCommentsToCafe(member5, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("잼잼")
					.info("연남동에 생긴 신상 브런치카페")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로29길", "지하1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("잼잼").get();
			addCommentsToCafe(member2, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("치플레")
					.info("살면서 단 한번도 경험해보지 못한 인생 수플레 치즈케이크 특별한 당신에게 선물합니다")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로", "262-9 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("치플레").get();
			addCommentsToCafe(member2, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("티크닉")
					.info("휴식의 가치를 추구하고 건강한 티라이프를 제안합니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로", "147 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("티크닉").get();
			addCommentsToCafe(member3, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("서울페이스트리 연남점")
					.info("서울페이스트리는 2016년에 오픈한 크로넛 전문 베이커리 카페입니다. 프랑스 최고급 밀가루와 버터를 사용하여 풍부한 맛과 향을 자랑합니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로41길", "10 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("서울페이스트리 연남점").get();
			addCommentsToCafe(member5, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("씨스루 홍대연남점")
					.info("유니크한 더티커피 마시러 씨스루 연남동 카페.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로", "266-6 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("씨스루 홍대연남점").get();
			addCommentsToCafe(member2, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("얼스어스")
					.info("노플라스틱 카페 얼스어스의 포장 예약입니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로", "150")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("얼스어스").get();
			addCommentsToCafe(member3, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("믜요")
					.info("믜요는 한식디저트를 기반으로 차와 어울리는 다과를 만듭니다. 좋은 재료를 선별하여 직접 손질하고 하나하나의 과정에 정성을 드립니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로", "256-10 2층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("믜요").get();
			addCommentsToCafe(member3, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("꽈페 연남본점")
					.info("천재같은 꽈배기 카페겸 니트샵.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로46길", "20 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("꽈페 연남본점").get();
			addCommentsToCafe(member5, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("꿀넹쿠키 연남점")
					.info("*매장 픽업예약은 인스타그램 dm or 네이버톡톡 으로만 받습니다!(전화X)"
						+ "예약자성명, 핸드폰번호, 픽업시간, 품목을 보내주세요."
						+ "꿀넹쿠키 인스타그램 : @ggulneng_cookies내용 더보기")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로", "255-1 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("꿀넹쿠키 연남점").get();
			addCommentsToCafe(member5, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("1984")
					.info("편집샵과 북카페로 유명한 연남동 카페")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "동교로", "194 혜원빌딩 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("1984").get();
			addCommentsToCafe(member5, cafe);
			addOpeningHoursToCafe(cafe);

			// 홍대 카페
			cafeRepository.save(
				Cafe.builder()
					.name("츄로101 홍대본점")
					.info("블루리본 12년 연속등재! 주문즉시 만들어지는 다양한 츄러스를 즐겨보세요!")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "어울마당로", "128")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("츄로101 홍대본점").get();
			addCommentsToCafe(member2, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("라헬의부엌 홍대점")
					.info("라헬의부엌은 계절마다 준비되는 정성담은 메뉴를 선보이는 수플레 브런치 맛집 겸 카페(cate + meal)입니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "와우산로29길", "47 1,2층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("라헬의부엌 홍대점").get();
			addCommentsToCafe(member3, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("콤파일")
					.info("훌륭한 커피만 모아 소개할 수 있도록 노력하겠습니다."
					+ "방문 해주신 모든분들의 한모금에 위로가 될 수 있도록. 고맙습니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "잔다리로73", "1F")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("콤파일").get();
			addCommentsToCafe(member1, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("아벨롭")
					.info("카페, 그리고 베이커리 <아벨롭>입니다."
					+ "저희는 단순한 카페 & 베이커리가 아닌 감각적인 공간과 스페셜티, 그리고 그 밖에 음료를 제공합니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "양화로15안길 6", "Avelop")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("아벨롭").get();
			addCommentsToCafe(member3, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("오퍼 카페")
					.info("더 많은 이야기와 디자인을 경험할 수 있는 공간, 홍대카페 오퍼카페를 소개합니다."
					+ "Offet는 따스하고 부드러운 분위기에서 쉴 수 있는 쉼터입니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "홍익로5길", "19")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("오퍼 카페").get();
			addCommentsToCafe(member4, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("아오이토리")
					.info("야끼소바빵과 명란바게트가 맛있는 홍대 빵집"
					+ "공지사항 및 최신 소식은 인스타그램에서 확인하실 수 있습니다:)")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "와우산로28길", "8 K.C 빌딩")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("아오이토리").get();
			addCommentsToCafe(member5, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("카페 공명 홍대점")
					.info("공명만의 예술적인 분위기, 진심을 다해 내린 커피 한 잔, 정성을 듬뿍 담은 베이커리를 즐겨보세요.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "와우산로17길", "11-8 지하1층, 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("카페 공명 홍대점").get();
			addCommentsToCafe(member3, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("모센즈스위트")
					.info("가장 맛있는 아랍 디저트, 모센즈스위트에서 즐겨보세요!"
					+ "모센즈스위트는 게이마르(카이막), 쿠나파 등 중동의 유명 디저트를 만들고 있어요.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "어울마당로", "51-1 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("모센즈스위트").get();
			addCommentsToCafe(member2, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("스탠스커피")
					.info("아인슈페너가 맛있는 고즈넉한 카페")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "와우산로11길", "9 1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("스탠스커피").get();
			addCommentsToCafe(member4, cafe);
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("트라이브 홍대점")
					.info("수플레 전문점 트라이브 홍대점입니다."
					+ "제주에서만 맛 볼수 있던 트라이브의 시그니처 수플레를 서울에서 인사드립니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "어울마당로", "100-12 지1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("트라이브 홍대점").get();
			addCommentsToCafe(member2, cafe);
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("콜린")
					.info("분위기 좋고 음료가 맛있는 홍대 플라워 카페")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "어울마당로", "45")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("콜린").get();
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("퍼스트커피랩 홍대점")
					.info("전문 바리스타 양성 기관인 [퍼스트 아카데미]에서 운영하는 카페입니다."
					+ "퍼스트커피랩은 최상의 커피와 직접 생산한 다양한 디저트를 제공하고 있습니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "신촌로4길", "9")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("퍼스트커피랩 홍대점").get();
			addOpeningHoursToCafe(cafe);
			addReviewsToCafe(member1, cafe);
			addReviewsToCafe(member2, cafe);
			addReviewsToCafe(member3, cafe);
			addReviewsToCafe(member4, cafe);
			addReviewsToCafe(member5, cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("더블유오앤")
					.info("W:ON COFFEE 매일 12시부터 새벽 3시까지 운영합니다!")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "잔다리로6길", "20-9 1층, B1층")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("더블유오앤").get();
			addOpeningHoursToCafe(cafe);

			cafeRepository.save(
				Cafe.builder()
					.name("멧라운지")
					.info("METLOUNGE는 커피와 음료, 와인과 맥주 등 다양한 음료가 있어 원하는 모든 취향을 저격하며 방문해주시는 모든 분들께 편안한 휴식을 제공하는 곳입니다."
						+ "매장에서 명함을 받아 미리 예약을 해주시면 02시까지 영업합니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "어울마당로5길", "42 1층 멧라운지 metlounge")
					)
					.openingHours(new ArrayList<>())
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);
			cafe = cafeRepository.findByName("멧라운지").get();
			addOpeningHoursToCafe(cafe);
		}

		// Methods
		private void addReviewsToCafe(Member member, Cafe cafe) {
			if (cafe.getCafeId() % 3 == 1) {
				reviewRepository.save(
					Review.builder()
						.cafeCongestion(CafeCongestion.HIGH)
						.isClean(true)
						.hasPlug(true)
						.cafe(cafe)
						.member(member)
						.build()
				);
			} else if (cafe.getCafeId() % 3 == 2) {
				reviewRepository.save(
					Review.builder()
						.cafeCongestion(CafeCongestion.MEDIUM)
						.isClean(true)
						.hasPlug(false)
						.cafe(cafe)
						.member(member)
						.build()
				);
			} else if (cafe.getCafeId() % 3 == 0) {
				reviewRepository.save(
					Review.builder()
						.cafeCongestion(CafeCongestion.LOW)
						.isClean(false)
						.hasPlug(false)
						.cafe(cafe)
						.member(member)
						.build()
				);
			}
		}

		private void addOpeningHoursToCafe(Cafe cafe) {
			openingHourRepository.save(
				OpeningHour.builder()
					.dayOfWeek(DayOfWeek.MONDAY)
					.openTime(LocalTime.of(9, 0))
					.closeTime(LocalTime.of(20, 0))
					.cafe(cafe)
					.build()
			);
			openingHourRepository.save(
				OpeningHour.builder()
					.dayOfWeek(DayOfWeek.TUESDAY)
					.openTime(LocalTime.of(9, 0))
					.closeTime(LocalTime.of(20, 0))
					.cafe(cafe)
					.build()
			);
			openingHourRepository.save(
				OpeningHour.builder()
					.dayOfWeek(DayOfWeek.WEDNESDAY)
					.openTime(LocalTime.of(9, 0))
					.closeTime(LocalTime.of(20, 0))
					.cafe(cafe)
					.build()
			);
			openingHourRepository.save(
				OpeningHour.builder()
					.dayOfWeek(DayOfWeek.THURSDAY)
					.openTime(LocalTime.of(9, 0))
					.closeTime(LocalTime.of(20, 0))
					.cafe(cafe)
					.build()
			);
			openingHourRepository.save(
				OpeningHour.builder()
					.dayOfWeek(DayOfWeek.FRIDAY)
					.openTime(LocalTime.of(9, 0))
					.closeTime(LocalTime.of(20, 0))
					.cafe(cafe)
					.build()
			);
			openingHourRepository.save(
				OpeningHour.builder()
					.dayOfWeek(DayOfWeek.SATURDAY)
					.openTime(LocalTime.of(9, 0))
					.closeTime(LocalTime.of(20, 0))
					.cafe(cafe)
					.build()
			);
			openingHourRepository.save(
				OpeningHour.builder()
					.dayOfWeek(DayOfWeek.SUNDAY)
					.openTime(LocalTime.of(9, 0))
					.closeTime(LocalTime.of(20, 0))
					.cafe(cafe)
					.build()
			);
		}

		private void addCommentsToCafe(Member member, Cafe cafe) {
			commentRepository.save(
				Comment.builder()
					.content("여기 카페 너무 트렌디해요!")
					.cafe(cafe)
					.member(member)
					.build()
			);
			commentRepository.save(
				Comment.builder()
					.content("카페가 너무 깨끗하고 좋아요~ <3")
					.cafe(cafe)
					.member(member)
					.build()
			);
			commentRepository.save(
				Comment.builder()
					.content("여기 사장님이 너무 잘생겼어요.")
					.cafe(cafe)
					.member(member)
					.build()
			);
			commentRepository.save(
				Comment.builder()
					.content("공부하기 좋아요!")
					.cafe(cafe)
					.member(member)
					.build()
			);
			commentRepository.save(
				Comment.builder()
					.content("케이크가 정말 맛있어요!!!")
					.cafe(cafe)
					.member(member)
					.build()
			);
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
