package com.tbooke.tbookeuser.model.response;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

/**
 * 공통 응답 모델
 */
@Getter
@Setter
public class CommonResult {

	// 응답 성공 여부 (True / False)
	private boolean success;

	// 응답 코드(0 보다 크면(양수) 정상, 0보다 작으면(음수) 비정상)
	private int code;

	// 응답 메세지
	private String msg;
}
