package com.tbooke.tbookeuser.service.oauth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.tbooke.tbookeuser.config.security.TokenProvider;
import com.tbooke.tbookeuser.dto.google.GoogleAccessTokenResponseDto;
import com.tbooke.tbookeuser.dto.google.GoogleProfileResponseDto;
import com.tbooke.tbookeuser.entity.User;
import com.tbooke.tbookeuser.entity.UserRole;
import com.tbooke.tbookeuser.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleUserService {

	private final String GOOGLE_TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token";
	private final String GOOGLE_PROFILE_REQUEST_URL = "https://www.googleapis.com/oauth2/v1/userinfo";

	private RestTemplate restTemplate = new RestTemplate();

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserRepository userRepository;

	private final TokenProvider tokenProvider;

	@Value("${social.google.clientId}")
	private String clientId;

	@Value("${social.google.secret}")
	private String secret;

	@Value("${social.google.redirect}")
	private String redirectUri;

	/**
	 * 실제 구글 로그인을 통해서 JWT 토큰을 발급하는 메소드
	 * @param code 구글에서 주는 임시 코드
	 * @return JWT 토큰
	 */
	public String googleLogin(String code) {
		User user;

		// 1. 구글로부터 access_code로 받는다.
		GoogleAccessTokenResponseDto tokenResponseDto = getAccessToken(code);

		// 2. 구글로부터 받은 access_code로 유저 프로필을 받아온다.
		GoogleProfileResponseDto profileResponseDto = getUserInfo(tokenResponseDto.getAccess_token());

		// 3. 구글 이메일로 부터 기존에 사용자가 있는지 조회한다.
		Optional<User> optionalUser = userRepository.findByEmail(profileResponseDto.getEmail());

		if (optionalUser.isPresent()) {
			// 4-1. 사용자가 있으면 그 사용자를 가져온다.
			user = optionalUser.get();
		} else {

			// 4-2. 사용자가 없으면 새로 회원가입을 진행한다.
			String password = bCryptPasswordEncoder.encode("tbookSecret"); // 임의의 패스워드
			String providerId = profileResponseDto.getId(); // 구글 공급자 ID

			// 4-2-1. 새로운 유저를 생성한다.
			user = User.builder()
				.email(profileResponseDto.getEmail())
				.name(profileResponseDto.getName())
				.password(password)
				.address(null)
				.phone(null)
				.mode(null)
				.role(UserRole.ROLE_USER)
				.provider("google")
				.providerId(providerId)
				.build();

			userRepository.save(user);
		}

		// 5. 만들어진 토큰을 반환한다.
		return tokenProvider.createToken(user.getEmail(), user.getRole().toString());
	}

	/**
	 * 구글로 부터 access_code를 요청한다.
	 * @param code 구글에서 부여하는 임시 코드
	 * @return access_code
	 */
	public GoogleAccessTokenResponseDto getAccessToken(String code) {

		// Set Body
		Map<String, String> params = new HashMap<>();
		params.put("code", code);
		params.put("client_id", clientId);
		params.put("client_secret", secret);
		params.put("redirect_uri", redirectUri);
		params.put("grant_type", "authorization_code");

		// 액세스 코드를 받기 위해 구글로부터 엑세스 토큰을 요청한다.
		// reference https://developers.google.com/identity/protocols/oauth2/web-server?hl=ko
		GoogleAccessTokenResponseDto responseDto = restTemplate.postForObject(GOOGLE_TOKEN_REQUEST_URL, params,
			GoogleAccessTokenResponseDto.class);

		// 액세스 토큰 저장
		return responseDto;
	}

	/**
	 * 발급된 access_token으로 유저 프로필 정보를 가져온다.
	 * @param accessToken
	 * @return 유저 프로필을 담은 GoogleProfileResponse 객체
	 */
	public GoogleProfileResponseDto getUserInfo(String accessToken) {

		// Set Headers with Bearer Access Token
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);

		// 구글로 부터 유저 프로필 정보를 가져온다
		// refrence https://developers.google.com/identity/protocols/oauth2/web-server?hl=ko
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

		// 결과값을 GoogleProfileResponse 객체에 넣어서 가져온다.
		ResponseEntity<GoogleProfileResponseDto> response = restTemplate.exchange(GOOGLE_PROFILE_REQUEST_URL,
			HttpMethod.GET, request,
			GoogleProfileResponseDto.class);

		return response.getBody();
	}
}
