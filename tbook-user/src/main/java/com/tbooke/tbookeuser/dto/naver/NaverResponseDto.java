package com.tbooke.tbookeuser.dto.naver;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NaverResponseDto<T> {

	String resultCode;
	String message;
	private T response;
}
