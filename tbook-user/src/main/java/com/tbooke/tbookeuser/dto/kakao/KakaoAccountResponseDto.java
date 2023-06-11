package com.tbooke.tbookeuser.dto.kakao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoAccountResponseDto {
	private KakaoProfileResponseDto profile;
	private String name;
	private String email;
}
