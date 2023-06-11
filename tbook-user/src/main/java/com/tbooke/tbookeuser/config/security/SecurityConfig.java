package com.tbooke.tbookeuser.config.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.tbooke.tbookeuser.filter.JwtAuthenticationFilter;
import com.tbooke.tbookeuser.config.security.TokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private final TokenProvider tokenProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// 서버에 인증정보를 저장 안하기 때문에 csrf 비활성화
			.cors().configurationSource(request -> {
				var cors = new CorsConfiguration();
				cors.setAllowedOrigins(List.of("http://localhost:3000"));
				cors.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
				cors.setAllowedHeaders(List.of("*"));
				cors.addExposedHeader("Authorization");
				return cors;

			})

			.and()
			.csrf().disable()


			// Security Session 비활성화
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

			// from 로그인 비활성화하고 jwt를 이용하여 인증한다.
			.and()
			.formLogin().disable()

			// 기존에 아이디, PW로 인증하는 행위 비활성화
			.httpBasic().disable()

			// URL 인가 설정
			.authorizeRequests()
//			.antMatchers("/api/v1/user/google", "/api/v1/user/naver", "/api/v1/user/kakao").permitAll()
//				.antMatchers("/kakao").permitAll()
//			.antMatchers("/api/v1/user/login", "/user-service/users/signup", "/actuator/info").permitAll()
//			.anyRequest().hasRole("USER")
			.anyRequest().permitAll()

			// JWTAuthentication Filter 적용
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
