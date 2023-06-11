package com.tbooke.tbookeuser.dto.kakao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoProfileResponseDto {

	private String nickname;
	private String thumbnail_image_url;
	private String profile_image_url;
	private boolean is_default_image;
}
