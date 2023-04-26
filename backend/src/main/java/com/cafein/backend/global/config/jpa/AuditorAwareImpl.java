package com.cafein.backend.global.config.jpa;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.util.StringUtils;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Override
	public Optional<String> getCurrentAuditor() {
		String modifiedBy = httpServletRequest.getRequestURI();
		if (!StringUtils.hasText(modifiedBy)) {
			modifiedBy = "unknown";
		}
		return Optional.of(modifiedBy);
	}
}
