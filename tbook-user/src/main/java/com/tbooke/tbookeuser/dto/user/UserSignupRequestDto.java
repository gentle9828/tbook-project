package com.tbooke.tbookeuser.dto.user;

import com.tbooke.tbookeuser.entity.AIMode;
import com.tbooke.tbookeuser.entity.User;
import com.tbooke.tbookeuser.entity.UserRole;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupRequestDto {

	private String email;
	private String password;
	private String name;
	private String address;
	private String phone;
	private AIMode mode;

	public void setPassword(String password) {
		this.password = password;
	}

	public User toEntity() {
		return User.builder()
			.email(email)
			.password(password)
			.name(name)
			.address(address)
			.phone(phone)
			.mode(mode)
			.role(UserRole.ROLE_USER)
			.build();
	}
}
