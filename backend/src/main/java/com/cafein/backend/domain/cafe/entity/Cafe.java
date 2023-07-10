package com.cafein.backend.domain.cafe.entity;

import static javax.persistence.CascadeType.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.cafein.backend.domain.cafe.constant.Local;
import com.cafein.backend.domain.comment.entity.Comment;
import com.cafein.backend.domain.common.Address;
import com.cafein.backend.domain.common.BaseTimeEntity;
import com.cafein.backend.domain.openinghours.entity.OpeningHour;
import com.cafein.backend.domain.review.entity.Review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cafe extends BaseTimeEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cafeId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String info;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Local local;

	@Embedded
	private Address address;

	@Column(length = 20)
	private String phoneNumber;

	@Column(length = 20)
	private String latitude;

	@Column(length = 20)
	private String longitude;

	@OneToMany(mappedBy = "cafe", cascade = ALL)
	private List<OpeningHour> openingHours = new ArrayList<>();

	@OneToMany(mappedBy = "cafe", cascade = ALL)
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "cafe", cascade = ALL)
	private List<Comment> comments = new ArrayList<>();

	@Builder
	public Cafe(String name, String info, Local local, Address address, List<OpeningHour> openingHours,
		List<Review> reviews, List<Comment> comments) {
		this.name = name;
		this.info = info;
		this.local = local;
		this.address = address;
		this.openingHours = openingHours;
		this.reviews = reviews;
		this.comments = comments;
	}
}
