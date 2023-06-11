package com.tbooke.tbookeuser.dto.user;

import com.tbooke.tbookeuser.entity.AIMode;
import com.tbooke.tbookeuser.entity.UserRole;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDetailResponseDto {
	private final Long id;

	private final String email;

	private final String name;

	private final String address;

	private final String phone;

	private final AIMode aiMode;

	private final UserRole role;

	private final String providerId;

	private final String provider;

	@Builder
	public UserDetailResponseDto(Long id, String email, String name, String address, String phone, AIMode aiMode,
		UserRole role, String providerId, String provider) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.aiMode = aiMode;
		this.role = role;
		this.providerId = providerId;
		this.provider = provider;
	}
}
