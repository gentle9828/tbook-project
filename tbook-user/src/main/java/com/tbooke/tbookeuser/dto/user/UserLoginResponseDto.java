package com.tbooke.tbookeuser.dto.user;

import java.time.LocalDateTime;

import com.tbooke.tbookeuser.entity.UserRole;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
public class UserLoginResponseDto {

	private final Long id;
	private final String email;
	private final UserRole role;
	private final LocalDateTime createdDate;

	@Builder
	public UserLoginResponseDto(Long id, String email, UserRole role, LocalDateTime createdDate) {
		this.id = id;
		this.email = email;
		this.role = role;
		this.createdDate = createdDate;
	}
}
