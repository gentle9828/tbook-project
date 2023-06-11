package com.tbooke.tbookeuser.dto.naver;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NaverAccessTokenResponseDto {

	private String access_token;

	private String refresh_token;

	private String token_type;

	private int expires_in;

	private String error;

	private String error_description;


}
