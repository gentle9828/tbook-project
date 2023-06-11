package com.tbooke.tbookeuser.controller;

import javax.security.auth.login.LoginContext;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tbooke.tbookeuser.config.security.TokenProvider;
import com.tbooke.tbookeuser.dto.user.UserLoginResponseDto;
import com.tbooke.tbookeuser.service.oauth.GoogleUserService;
import com.tbooke.tbookeuser.service.oauth.KakaoUserService;
import com.tbooke.tbookeuser.service.oauth.NaverUserService;
import com.tbooke.tbookeuser.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class OAuthController {

	private final NaverUserService naverUserService;
	private final KakaoUserService kakaoUserService;
	private final GoogleUserService googleUserService;

	private final UserService userService;

	private final TokenProvider tokenProvider;

	@GetMapping("/google")
	public ResponseEntity<UserLoginResponseDto> googleLogin(@RequestParam("code") String code) {
		// Set Hedaer
		HttpHeaders httpHeaders = new HttpHeaders();
		String token = googleUserService.googleLogin(code);

		httpHeaders.add("Authorization", "Bearer " + token);

		UserLoginResponseDto loginResponseDto = userService.login(tokenProvider.getUserEmail(token),"tbookSecret" );

		return ResponseEntity.ok()
			.headers(httpHeaders)
			.body(loginResponseDto);

	}

	@GetMapping("/naver")
	public ResponseEntity<UserLoginResponseDto> naverLogin(@RequestParam("code")String code){

		// Set Hedaer
		HttpHeaders httpHeaders = new HttpHeaders();
		String token = naverUserService.naverLogin(code);

		httpHeaders.add("Authorization", "Bearer " + token);
		UserLoginResponseDto loginResponseDto = userService.login(tokenProvider.getUserEmail(token),"tbookSecret" );

		return ResponseEntity.ok()
			.headers(httpHeaders)
			.body(loginResponseDto);
	}

	@GetMapping("/kakao")
	public ResponseEntity<UserLoginResponseDto> kakaoLogin(@RequestParam("code")String code){
		// Set Hedaer
		HttpHeaders httpHeaders = new HttpHeaders();
		String token = kakaoUserService.kakaoLogin(code);

		httpHeaders.add("Authorization", "Bearer " + token);
		UserLoginResponseDto loginResponseDto = userService.login(tokenProvider.getUserEmail(token),"tbookSecret" );

		return ResponseEntity.ok()
			.headers(httpHeaders)
			.body(loginResponseDto);
	}
}
