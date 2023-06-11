package com.tbooke.tbookeuser.advice;

import static com.tbooke.tbookeuser.service.response.ErrorCode.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tbooke.tbookeuser.advice.exception.UserExistCException;
import com.tbooke.tbookeuser.advice.exception.UserNotFoundCException;
import com.tbooke.tbookeuser.model.response.CommonResult;
import com.tbooke.tbookeuser.service.response.CommonResponse;
import com.tbooke.tbookeuser.service.response.ErrorCode;
import com.tbooke.tbookeuser.service.response.ResponseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

	private final ResponseService responseService;

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult defaultException(HttpServletRequest request, Exception e) {
		return responseService.getFailResult(UnknownException.getCode(), UnknownException.getDescription());
	}

	@ExceptionHandler(UserExistCException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult UserExistException(HttpServletRequest request, UserExistCException e) {
		return responseService.getFailResult(UserExistException.getCode(), UserExistException.getDescription());
	}

	@ExceptionHandler(UserNotFoundCException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected  CommonResult userNotFoundException(HttpServletRequest request, UserNotFoundCException e) {
		return responseService.getFailResult(UserNotFoundException.getCode(), UserNotFoundException.getDescription());
	}


}
