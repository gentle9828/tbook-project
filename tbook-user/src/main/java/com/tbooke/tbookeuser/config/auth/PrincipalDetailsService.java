package com.tbooke.tbookeuser.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tbooke.tbookeuser.entity.User;
import com.tbooke.tbookeuser.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	/**
	 * login 요청시 호출되는 메소드
	 * 해당 로그인이 완료되면 @AuthenticationPrincipal 어노테이션 생성
	 * @param email the email identifying the user whose data is required.
	 * @return Authentication 객체(UserDetails)
	 * @throws UsernameNotFoundException
	 */

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 이메일이 없습니다. email = " + email));

		return new PrincipalDetails(user);
	}
}
