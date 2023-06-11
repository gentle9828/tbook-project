package com.tbooke.tbookeuser.model.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 다중 응답 모델
 */
@Getter
@Setter
public class ListResult<T> extends CommonResult {

	// 여러개의 DTO들
	private List<T> list;
}
