package com.tbooke.tbookeuser.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 단일 응답 모델
 */
@Getter
@Setter
public class SingleResult<T> extends CommonResult {

	// DTO의 응답 내용
	private T data;
}
