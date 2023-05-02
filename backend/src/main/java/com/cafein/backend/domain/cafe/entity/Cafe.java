package com.cafein.backend.domain.cafe.entity;

import static javax.persistence.CascadeType.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.cafein.backend.domain.Review.entity.Review;
import com.cafein.backend.domain.comment.entity.Comment;
import com.cafein.backend.domain.common.Address;
import com.cafein.backend.domain.common.BaseTimeEntity;
import com.cafein.backend.domain.openinghours.entity.OpeningHour;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cafe extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cafeId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String info;

	@Embedded
	private Address address;

	@OneToMany(mappedBy = "cafe", cascade = ALL)
	private List<OpeningHour> openingHours = new ArrayList<>();

	@OneToMany(mappedBy = "cafe", cascade = ALL)
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "cafe", cascade = ALL)
	private List<Comment> comments = new ArrayList<>();
}
