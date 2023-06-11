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
public class UserUpdateRequestDto {

	private String password;
	private String name;
	private String address;
	private String phone;
	private AIMode mode;

	public void setPassword(String password) {
		this.password = password;
	}

}
