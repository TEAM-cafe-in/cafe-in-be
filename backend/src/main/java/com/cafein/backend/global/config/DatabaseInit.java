package com.cafein.backend.global.config;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.domain.Review.respository.ReviewRepository;
import com.cafein.backend.domain.cafe.constant.Local;
import com.cafein.backend.domain.cafe.entity.Cafe;
import com.cafein.backend.domain.cafe.repository.CafeRepository;
import com.cafein.backend.domain.comment.repository.CommentRepository;
import com.cafein.backend.domain.common.Address;
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
		private final ReviewRepository reviewRepository;
		private final CommentRepository commentRepository;
		private final OpeningHourRepository openingHourRepository;

		public void dbInit() {
			// OpeningHour Sample Data
			List<OpeningHour> openingHours = Arrays.asList(
				new OpeningHour(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(20, 0)),
				new OpeningHour(DayOfWeek.TUESDAY, LocalTime.of(9, 0), LocalTime.of(20, 0)),
				new OpeningHour(DayOfWeek.WEDNESDAY, LocalTime.of(9, 0), LocalTime.of(20, 0)),
				new OpeningHour(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(20, 0)),
				new OpeningHour(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(20, 0)),
				new OpeningHour(DayOfWeek.SATURDAY, LocalTime.of(0, 0), LocalTime.of(0, 0)),
				new OpeningHour(DayOfWeek.SUNDAY, LocalTime.of(0, 0), LocalTime.of(0, 0))
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
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

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
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

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
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("터틀힙")
					.info("누적판매 10만개,"
						+ "주문제작 케이크 전문점 터틀힙입니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "연남로1길", "44 1층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("버터앤쉘터 연남점")
					.info("시그니처 커피 & 수제 디저트 카페 연남 버터앤쉘터 입니다."
						+ "*반려동물 동반은 야외 좌석만 가능합니다.내용 더보기")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로", "170 102호")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

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
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

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
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("청수당공명")
					.info("청수당의 다섯번째 걸음, 청수당-공명 입니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로", "152")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("파이인더샵")
					.info("연남동 한적한 주택가 사이, 특별한 공간입니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로27길", "26")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("코코로카라")
					.info("안녕하세요, 마음을 담은 작은 과자점 코코로카라 입니 다 :)")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "연남로1길", "41")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("랜디스도넛 연남점")
					.info("아이언맨2에서 스타크가 먹은 랜디스도넛 연남동점")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로", "247")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("파롤앤랑그")
					.info("네모난 파이가 귀엽고 맛있는 연남동 디저트 카페")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로29안길", "8")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("에브리데이해피벌스데이")
					.info("매일 생일 같은 연남동 카페")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "연희로", "3층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("땡스오트 연남")
					.info("땡스오트가 생각하는 좋은 요거트와 그래놀라는 인위적인 첨가물을 넣지 않고 좋은 원재료를 사용하는 것 입니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로23길", "68")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("잼잼")
					.info("연남동에 생긴 신상 브런치카페")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로29길", "지하1층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("치플레")
					.info("살면서 단 한번도 경험해보지 못한 인생 수플레 치즈케이크 특별한 당신에게 선물합니다")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로", "262-9 1층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("티크닉")
					.info("휴식의 가치를 추구하고 건강한 티라이프를 제안합니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로", "147 1층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("서울페이스트리 연남점")
					.info("서울페이스트리는 2016년에 오픈한 크로넛 전문 베이커리 카페입니다. 프랑스 최고급 밀가루와 버터를 사용하여 풍부한 맛과 향을 자랑합니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로41길", "10 1층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("씨스루 홍대연남점")
					.info("유니크한 더티커피 마시러 씨스루 연남동 카페.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로", "266-6 1층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("얼스어스")
					.info("노플라스틱 카페 얼스어스의 포장 예약입니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "성미산로", "150")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("믜요")
					.info("믜요는 한식디저트를 기반으로 차와 어울리는 다과를 만듭니다. 좋은 재료를 선별하여 직접 손질하고 하나하나의 과정에 정성을 드립니다.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로", "256-10 2층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("꽈페 연남본점")
					.info("천재같은 꽈배기 카페겸 니트샵.")
					.local(Local.YEONNAM)
					.address(
						createAddress("서울", "서울시 마포구", "동교로46길", "20 1층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

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
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("1984")
					.info("편집샵과 북카페로 유명한 연남동 카페")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "동교로", "194 혜원빌딩 1층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			// 홍대 카페
			cafeRepository.save(
				Cafe.builder()
					.name("츄로101 홍대본점")
					.info("블루리본 12년 연속등재! 주문즉시 만들어지는 다양한 츄러스를 즐겨보세요!")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "어울마당로", "128")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("라헬의부엌 홍대점")
					.info("라헬의부엌은 계절마다 준비되는 정성담은 메뉴를 선보이는 수플레 브런치 맛집 겸 카페(cate + meal)입니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "와우산로29길", "47 1,2층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("콤파일")
					.info("훌륭한 커피만 모아 소개할 수 있도록 노력하겠습니다."
					+ "방문 해주신 모든분들의 한모금에 위로가 될 수 있도록. 고맙습니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "잔다리로73", "1F")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("아벨롭")
					.info("카페, 그리고 베이커리 <아벨롭>입니다."
					+ "저희는 단순한 카페 & 베이커리가 아닌 감각적인 공간과 스페셜티, 그리고 그 밖에 음료를 제공합니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "양화로15안길 6", "Avelop")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("오퍼 카페")
					.info("더 많은 이야기와 디자인을 경험할 수 있는 공간, 홍대카페 오퍼카페를 소개합니다."
					+ "Offet는 따스하고 부드러운 분위기에서 쉴 수 있는 쉼터입니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "홍익로5길", "19")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("아오이토리")
					.info("야끼소바빵과 명란바게트가 맛있는 홍대 빵집"
					+ "공지사항 및 최신 소식은 인스타그램에서 확인하실 수 있습니다:)")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "와우산로28길", "8 K.C 빌딩")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("카페 공명 홍대점")
					.info("공명만의 예술적인 분위기, 진심을 다해 내린 커피 한 잔, 정성을 듬뿍 담은 베이커리를 즐겨보세요.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "와우산로17길", "11-8 지하1층, 1층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("모센즈스위트")
					.info("가장 맛있는 아랍 디저트, 모센즈스위트에서 즐겨보세요!"
					+ "모센즈스위트는 게이마르(카이막), 쿠나파 등 중동의 유명 디저트를 만들고 있어요.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "어울마당로", "51-1 1층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("스탠스커피")
					.info("아인슈페너가 맛있는 고즈넉한 카페")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "와우산로11길", "9 1층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("트라이브 홍대점")
					.info("수플레 전문점 트라이브 홍대점입니다."
					+ "제주에서만 맛 볼수 있던 트라이브의 시그니처 수플레를 서울에서 인사드립니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "어울마당로", "100-12 지1층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("콜린")
					.info("분위기 좋고 음료가 맛있는 홍대 플라워 카페")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "어울마당로", "45")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("퍼스트커피랩 홍대점")
					.info("전문 바리스타 양성 기관인 [퍼스트 아카데미]에서 운영하는 카페입니다."
					+ "퍼스트커피랩은 최상의 커피와 직접 생산한 다양한 디저트를 제공하고 있습니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "신촌로4길", "9")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("더블유오앤")
					.info("W:ON COFFEE 매일 12시부터 새벽 3시까지 운영합니다!")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "잔다리로6길", "20-9 1층, B1층")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
					.build()
			);

			cafeRepository.save(
				Cafe.builder()
					.name("멧라운지")
					.info("METLOUNGE는 커피와 음료, 와인과 맥주 등 다양한 음료가 있어 원하는 모든 취향을 저격하며 방문해주시는 모든 분들께 편안한 휴식을 제공하는 곳입니다."
						+ "매장에서 명함을 받아 미리 예약을 해주시면 02시까지 영업합니다.")
					.local(Local.HONGDAE)
					.address(
						createAddress("서울", "서울시 마포구", "어울마당로5길", "42 1층 멧라운지 metlounge")
					)
					.openingHours(openingHours)
					.reviews(new ArrayList<>())
					.comments(new ArrayList<>())
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
