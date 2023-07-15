package com.cafein.backend.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafein.backend.domain.member.constant.MemberType;
import com.cafein.backend.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

	Optional<Member> findByRefreshToken(String refreshToken);

	Optional<Member> findByEmailAndMemberType(String email, MemberType memberType);
}
