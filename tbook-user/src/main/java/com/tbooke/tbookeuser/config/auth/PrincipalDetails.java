package com.tbooke.tbookeuser.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tbooke.tbookeuser.entity.User;

/**
 * 로그인을 진행 후 Security Session에 Authentication 타입으로 저장
 * Authentication 안에 User 정보를 불러오기 위한 클래스
 */
public class PrincipalDetails implements UserDetails {

	private final User user;

	public PrincipalDetails(User user) {
		this.user = user;
	}

	public String getEmail(){
		return user.getEmail();
	}

	/**
	 * 계정이 패스워드 리턴하는 메소드
	 * @return 패스워드
	 */
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	/**
	 * 계정의 이름을 리턴하는 메소드
	 * @return 유저 이름
	 */
	@Override
	public String getUsername() {
		return user.getName();
	}

	/**
	 * 계정이 만료되지 않았는지를 리턴하는 메소드
	 * True 리턴시 만료되지 않음.
	 * @return 계정이 만료되지 않음
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 계정이 잠겨있지 않은지를 리턴하는 메소드
	 * True 리턴시 계정이 잠겨있지 않음을 의미
	 * @return 계정이 잠겨있지 않음.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 계정의 패스워드가 만료되지 않았는지를 리턴한다.
	 * True 리턴하면 패스워드가 만료되지 않음을 의미
	 * @return 패스워드가 만료되지 않음
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 계정이 사용 가능한 계정인지를 리턴
	 * @return 사용 가능한 계정
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * 계정이 가지고 있는 권한 목록 리턴
	 * @return 권한 목록
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole().name();
			}
		});
		return collect;
	}

}
