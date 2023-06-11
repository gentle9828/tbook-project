package com.tbooke.tbookeuser.service.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tbooke.tbookeuser.advice.exception.UserExistCException;
import com.tbooke.tbookeuser.advice.exception.UserNotFoundCException;
import com.tbooke.tbookeuser.config.security.TokenProvider;
import com.tbooke.tbookeuser.dto.user.UserDetailResponseDto;
import com.tbooke.tbookeuser.dto.user.UserLoginResponseDto;
import com.tbooke.tbookeuser.dto.user.UserSignupRequestDto;
import com.tbooke.tbookeuser.dto.user.UserUpdateRequestDto;
import com.tbooke.tbookeuser.entity.User;
import com.tbooke.tbookeuser.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;

	/**
	 * 일반 로그인 하기(ID와 PW로 로그인)
	 * @param email 사용자 이메일
	 * @param password 패스워드
	 * @return 유저정보
	 */
	@Transactional(readOnly = true)
	public UserLoginResponseDto login(String email, String password) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다."));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("패스워드가 잘못됐습니다.");
		}

		return UserLoginResponseDto.builder()
			.id(user.getId())
			.email(user.getEmail())
			.role(user.getRole())
			.createdDate(user.getCreatedDate())
			.build();
	}

	/**
	 * 새로운 사용자 회원가입 하기
	 * @param signupRequestDto 회원가입에 필요한 정보들(주소, 아이디, 비밀번호등등)
	 * @return JWT 토큰을 발급하기 위한 유저 객체
	 */
	@Transactional
	public User signup(UserSignupRequestDto signupRequestDto) {
		if (userRepository.findByEmail(signupRequestDto.getEmail()).isEmpty()) {
			String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
			signupRequestDto.setPassword(encodedPassword);

			return userRepository.save(signupRequestDto.toEntity());
		}

		throw new UserExistCException();
	}

	/**
	 * JWT 토큰으로 사용자 정보 조회하기
	 * @param token Authorization 헤더에 있는 JWT 토큰
	 * @return 유저 정보
	 */
	@Transactional(readOnly = true)
	public UserDetailResponseDto getUserDetail(String token)  {
		String email = tokenProvider.getUserEmail(token);
		User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundCException::new);

		return UserDetailResponseDto.builder()
			.id(user.getId())
			.email(user.getEmail())
			.name(user.getName())
			.address(user.getAddress())
			.phone(user.getPhone())
			.aiMode(user.getMode())
			.role(user.getRole())
			.provider(user.getProvider())
			.providerId(user.getProviderId())
			.build();
	}

	/**
	 * 유저 정보 업데이트하기
	 * @param token Authorization 헤더에 있는 JWT 토큰
	 * @param requestDto 수정된 사용자 정보
	 * @return 유저의 아이디
	 */
	@Transactional
	public Long update(String token, UserUpdateRequestDto requestDto){
		String email = tokenProvider.getUserEmail(token);
		User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다"));

		String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
		requestDto.setPassword(encodedPassword);

		user.update(requestDto);

		return user.getId();
	}

}
