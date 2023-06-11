package com.tbooke.tbookeuser.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	UserNotFoundException(-1000, "User Not Found"),
	UserExistException(-1001, "이미 존재하는 회원입니다."),
	UnknownException(-9999, "알수없는 오류가 발생했습니다.");

	private int code;
	private String description;
}
