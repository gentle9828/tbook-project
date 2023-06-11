package com.tbooke.tbookeuser.service.oauth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.tbooke.tbookeuser.config.security.TokenProvider;
import com.tbooke.tbookeuser.dto.kakao.KakaoAccessTokenResponseDto;
import com.tbooke.tbookeuser.dto.kakao.KakaoAccountResponseDto;
import com.tbooke.tbookeuser.dto.kakao.KakaoResponseDto;
import com.tbooke.tbookeuser.entity.User;
import com.tbooke.tbookeuser.entity.UserRole;
import com.tbooke.tbookeuser.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoUserService {

	private final String KAKAO_TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";
	private final String KAKAO_PROFILE_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;

	private final TokenProvider tokenProvider;

	private RestTemplate restTemplate = new RestTemplate();

	@Value("${social.kakao.clientId}")
	private String clientId;

	@Value("${social.kakao.secret}")
	private String secret;

	@Value("${social.kakao.redirect}")
	private String redirectUri;

	/**
	 * 실제 카카오 로그인을 통해서 JWT 토큰을 발급하는 메소드
	 *
	 * @param code 카카오에서 주는 임시 코드
	 * @return JWT 토큰
	 */
	public String kakaoLogin(String code) {
		User user;

		// 1. 카카오로부터 access_code로 받는다.
		KakaoAccessTokenResponseDto tokenResponseDto = getAccessToken(code);

		// 2. 카카오로부터 받은 access_code로 유저 프로필을 받아온다.
		KakaoResponseDto<KakaoAccountResponseDto> kakaoResponseDto = getUserInfo(tokenResponseDto.getAccess_token());
		KakaoAccountResponseDto profileResponseDto = kakaoResponseDto.getKakao_account();

		// 3. 카카오 이메일로 부터 기존에 사용자가 있는지 조회한다.
		Optional<User> optionalUser = userRepository.findByEmail(profileResponseDto.getEmail());

		if (optionalUser.isPresent()) {
			// 4-1. 사용자가 있으면 그 사용자를 가져온다.
			user = optionalUser.get();
		} else {

			// 4-2. 사용자가 없으면 새로 회원가입을 진행한다.
			String password = bCryptPasswordEncoder.encode("tbookSecret"); // 임의의 패스워드
			Long providerId = kakaoResponseDto.getId(); // 카카오 공급자 ID

			// 4-2-1. 새로운 유저를 생성한다.
			user = User.builder()
				.email(profileResponseDto.getEmail())
				.name(profileResponseDto.getProfile().getNickname())
				.password(password)
				.address(null)
				.phone(null)
				.mode(null)
				.role(UserRole.ROLE_USER)
				.provider("kakao")
				.providerId(providerId.toString())
				.build();

			userRepository.save(user);
		}

		// 5. 만들어진 토큰을 반환한다.
		return tokenProvider.createToken(user.getEmail(), user.getRole().toString());
	}

	/**
	 * 카카오로 부터 access_code를 요청한다.
	 *
	 * @param code 카카오에서 부여하는 임시 코드
	 * @return access_code
	 */
	public KakaoAccessTokenResponseDto getAccessToken(String code) {

		// Set Header
		// @WARNING MediaType.APPLICATION_FORM_URLENCODED를 설정하지 않으면 401 Authenticated 오류가 뜨니 주의
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("Accept", "application/json");

		// Set Body
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", clientId);
		params.add("client_secret", secret);
		params.add("redirect_uri", redirectUri);
		params.add("code", code);

		// Set http entity
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		// 액세스 코드를 받기 위한 파라미터 설정
		// reference https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-token
		ResponseEntity<KakaoAccessTokenResponseDto> responseDto = restTemplate.postForEntity(KAKAO_TOKEN_REQUEST_URL, request,
			KakaoAccessTokenResponseDto.class);

		// 액세스 토큰 저장
		return responseDto.getBody();
	}

	/**
	 * 발급된 access_token으로 유저 프로필 정보를 가져온다.
	 *
	 * @param accessToken
	 * @return 유저 프로필을 담은 GoogleProfileResponse 객체
	 */
	public KakaoResponseDto<KakaoAccountResponseDto> getUserInfo(String accessToken) {

		// 헤더에 accessToken을 넣는다.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);

		// 카카오로 부터 유저 프로필 정보를 가져온다
		// refrence https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

		// 결과값을 KakaoResponseDto<KakaoAccountResponseDto> 객체에 넣어서 가져온다.
		ResponseEntity<KakaoResponseDto<KakaoAccountResponseDto>> response = restTemplate.exchange(
			KAKAO_PROFILE_REQUEST_URL,
			HttpMethod.GET,
			request,
			new ParameterizedTypeReference<KakaoResponseDto<KakaoAccountResponseDto>>() {}
		);

		if (response.getBody() != null) {
			return response.getBody();
		}

		return null;

	}
}
