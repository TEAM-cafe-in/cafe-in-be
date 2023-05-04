package com.cafein.backend.api.home.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafein.backend.domain.member.entity.Member;
import com.cafein.backend.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HomeService {

	private final MemberService memberService;


}