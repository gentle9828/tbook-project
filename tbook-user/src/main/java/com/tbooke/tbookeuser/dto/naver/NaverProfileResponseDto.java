package com.tbooke.tbookeuser.dto.naver;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NaverProfileResponseDto {
	
	private String id;
	private String nickname;
	private String name;
	private String email;
	private String gender;
	private String age;
	private String birthday;
	private String profile_image;
	private String birthyear;
	private String mobile;


}
