package com.tbooke.tbookeuser.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.tbooke.tbookeuser.config.security.TokenProvider;

import lombok.RequiredArgsConstructor;

/**
 * JWT 토큰이 유효한 토큰인지 인증하기 위한 Filter
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

	private final TokenProvider tokenProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException, ServletException {

		// 헤더에서 토큰 가져오기
		String token = tokenProvider.resolveToken((HttpServletRequest)request);

		// 토믄이 유효한지 체크
		if (token != null && tokenProvider.validateToken(token)) {
			Authentication authentication = tokenProvider.getAuthentication(token);

			// Seucrity Context에 객체 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		// 다음 Filter 실행
		chain.doFilter(request, response);

	}
}
