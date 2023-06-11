package com.tbooke.tbookeuser.config.security;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.tbooke.tbookeuser.config.auth.PrincipalDetails;
import com.tbooke.tbookeuser.config.auth.PrincipalDetailsService;
import com.tbooke.tbookeuser.entity.User;
import com.tbooke.tbookeuser.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider {

	private final long expiredDate = 60 * 60 * 1000L;

	private final PrincipalDetailsService principalDetailsService;

	protected Key key;

	public TokenProvider(@Value("${jwt.secret}") String secretKey, PrincipalDetailsService principalDetailsService) {
		this.principalDetailsService = principalDetailsService;

		//시크릿 값을 decode해서 키 변수에 할당
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	// 토큰 생성
	public String createToken(String email, String role) {

		Claims claims = Jwts.claims().setSubject(email);
		claims.put("role", role);

		// 유효시간 설정
		Date now = new Date();
		Date validity = new Date(now.getTime() + this.expiredDate);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	// 토큰을 받아 클레임을 만들고 권한정보를 빼서 시큐리티 유저객체를 만들어 Authentication 객체 반환
	public Authentication getAuthentication(String token) {
		PrincipalDetails userDetails = (PrincipalDetails)principalDetailsService.loadUserByUsername(
			this.getUserEmail(token));

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUserEmail(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

	public String resolveToken(HttpServletRequest request) {
		if (request.getHeader("Authorization") != null){
			return request.getHeader("Authorization").replace("Bearer ", "");
		}
		return null;
	}

	// 토큰 유효성 검사
	public boolean validateToken(String token) {
		try {
			Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			return true;
		} catch (io.jsonwebtoken.security.SignatureException | MalformedJwtException e) {
			log.error("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.error("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.error("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.error("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}
}
