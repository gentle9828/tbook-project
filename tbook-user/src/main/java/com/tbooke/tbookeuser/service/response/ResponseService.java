package com.tbooke.tbookeuser.service.response;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tbooke.tbookeuser.model.response.CommonResult;
import com.tbooke.tbookeuser.model.response.ListResult;
import com.tbooke.tbookeuser.model.response.SingleResult;

@Service
public class ResponseService {

	/**
	 * 단 한개의 결과를 처리하는 단건 결과 처리 메소드
	 * @param data Dto 객체
	 * @return DTO를 포함한 성공 응답
	 * @param <T> DTO 타입
	 */
	public <T>SingleResult<T> getSingleResult(T data) {
		SingleResult<T> result = new SingleResult<>();
		result.setData(data);
		setSuccessResult(result);
		return result;
	}

	/**
	 * 여러개의 결과를 처리하는 다건 결과 처리 메소드
	 * @param list List 형식의 여러개의 DTO 객체
	 * @return List<Dto>를 포함한 성공 응답
	 * @param <T> DTO 타입
	 */
	public <T>ListResult<T> getListResult(List<T> list) {
		ListResult<T> result = new ListResult<>();
		result.setList(list);
		setSuccessResult(result);
		return result;
	}

	/**
	 * 성공 결과만 처리하는 메소드
	 * @return 성공결과
	 */
	public CommonResult getSuccessResult()  {
		CommonResult result = new CommonResult();
		setSuccessResult(result);
		return result;
	}

	/**
	 * 실패 결과만 처리하는 메소드
	 * @return 실패결과
	 */
	public CommonResult getFailResult(int code, String msg) {
		CommonResult result = new CommonResult();
		result.setSuccess(false);
		setFailResult(result, code, msg);
		return result;
	}

	/**
	 * API 요청 성공 시 응답 모델을 성공 데이터로 세팅하는 메소드
	 * @param result DTO를 담은 Result 객체
	 */
	private void setSuccessResult(CommonResult result){
		result.setSuccess(true);
		result.setCode(CommonResponse.SUCCESS.getCode());
		result.setMsg(CommonResponse.SUCCESS.getMsg());
	}

	/**
	 * API 요청 실패 시 응답 모델을 성공 데이터로 세팅하는 메소드
	 * @param result DTO를 담은 result 객체
	 */
	private void setFailResult(CommonResult result, int code, String msg){
		result.setSuccess(false);
		result.setCode(code);
		result.setMsg(msg);
	}
}
