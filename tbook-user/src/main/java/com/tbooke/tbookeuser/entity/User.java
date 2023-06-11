package com.tbooke.tbookeuser.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.web.bind.annotation.GetMapping;

import com.tbooke.tbookeuser.dto.user.UserUpdateRequestDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// 이메일
	@Column(nullable = false, unique = true)
	private String email;

	// 패스워드
	@Column(nullable = false)
	private String password;

	// 이름
	private String name;

	// 주소
	private String address;

	// 휴대전화
	private String phone;

	// 사용방식 종류(AI 추천 or 사용자)
	@Enumerated(EnumType.STRING)
	private AIMode mode;

	// 역할
	@Enumerated(EnumType.STRING)
	private UserRole role;

	private String providerId;

	private String provider;

	@Builder
	public User(String email, String password, String name, String address, String phone, AIMode mode, UserRole role,
		String providerId, String provider) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.mode = mode;
		this.role = role;
		this.providerId = providerId;
		this.provider = provider;
	}

	public void update(UserUpdateRequestDto requestDto){
		this.password = requestDto.getPassword();
		this.name = requestDto.getName();
		this.address = requestDto.getAddress();
		this.phone = requestDto.getPhone();
		this.mode = requestDto.getMode();

	}
}
