package com.tbooke.tbookeuser.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tbooke.tbookeuser.config.security.TokenProvider;
import com.tbooke.tbookeuser.dto.user.UserDetailResponseDto;
import com.tbooke.tbookeuser.dto.user.UserLoginRequestDto;
import com.tbooke.tbookeuser.dto.user.UserLoginResponseDto;
import com.tbooke.tbookeuser.dto.user.UserSignupRequestDto;
import com.tbooke.tbookeuser.dto.user.UserUpdateRequestDto;
import com.tbooke.tbookeuser.entity.User;
import com.tbooke.tbookeuser.model.response.SingleResult;
import com.tbooke.tbookeuser.service.user.UserService;
import com.tbooke.tbookeuser.service.response.ResponseService;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

	private final UserService userService;
	private final ResponseService responseService;
	private final TokenProvider tokenProvider;

	/**
	 * 작동 상태 확인
	 */
	@GetMapping("/health_check")
	public String status(HttpServletRequest request){
//        return String.format("Server port on %s",env.getProperty("local.server.port"));
		return String.format("Server port on %s",request.getServerPort());

	}

	@PostMapping("/login")
	public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto requestDto) {
		HttpHeaders httpHeaders = new HttpHeaders();
		UserLoginResponseDto loginResponseDto = userService.login(requestDto.getEmail(), requestDto.getPassword());
		String token = tokenProvider.createToken(loginResponseDto.getEmail(), loginResponseDto.getRole().toString());

		httpHeaders.add("Authorization", "Bearer " + token);

		return ResponseEntity.ok()
			.headers(httpHeaders)
			.body(loginResponseDto);
	}

	@PostMapping("/signup")
	public ResponseEntity<Long> signup(@RequestBody UserSignupRequestDto requestDto) {
		HttpHeaders httpHeaders = new HttpHeaders();
		User user = userService.signup(requestDto);
		String token = tokenProvider.createToken(user.getEmail(), user.getRole().toString());

		httpHeaders.add("Authorization", "Bearer " + token);

		return ResponseEntity.ok()
			.headers(httpHeaders)
			.body(user.getId());
	}

	@GetMapping("/me")
	public SingleResult<UserDetailResponseDto> getUserDetail(@RequestHeader("Authorization") String token) {
		if (token != null) {
			token = token.replace("Bearer ", "");
			return responseService
				.getSingleResult(userService.getUserDetail(token));
		}

		return null;
	}

	@PatchMapping("")
	public Long update(@RequestHeader("Authorization") String token, @RequestBody UserUpdateRequestDto requestDto) {
		if (token != null) {
			token = token.replace("Bearer ", "");
			return userService.update(token, requestDto);
		}

		return null;
	}

}
