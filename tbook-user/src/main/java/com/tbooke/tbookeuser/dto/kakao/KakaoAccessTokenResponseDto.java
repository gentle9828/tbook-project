package com.tbooke.tbookeuser.dto.kakao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoAccessTokenResponseDto {

	private String token_type;
	private String access_token;
	private String expires_in;
	private String refresh_token;
	private String refresh_token_expires_in;
	private String scope;
}
