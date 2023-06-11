package com.tbooke.tbookeuser.dto.google;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoogleAccessTokenResponseDto {
	private  String access_token;
	private  int expires_in;
	private  String token_type;
	private  String scope;
	private  String refresh_token;
}
