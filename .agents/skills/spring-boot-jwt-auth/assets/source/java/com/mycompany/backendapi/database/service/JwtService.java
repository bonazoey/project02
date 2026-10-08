package com.mycompany.backendapi.database.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtService {
	private long jwtDuration = 30L * 24 * 60 * 60 * 1000;
	
	private SecretKey secretKey;
	
	public JwtService(@Value("${jwt.secret.key}") String jwtSecretKey) throws Exception {
		byte[] bytes = jwtSecretKey.getBytes("UTF-8");
		secretKey = Keys.hmacShaKeyFor(bytes);
	}
	
	public String createJwt(String mid, String memail) {
		log.info(String.valueOf(jwtDuration));
		
		Date date = new Date(new Date().getTime() + jwtDuration);
		log.info(date.toString());
		
		String jwt = Jwts.builder()
			// 사용자의 식별값
			.subject(mid)			 
			// 사용자의 기타 정보
			.claim("memail", memail)
			// JWT 만료 기간
			.expiration(date)
			// 서명
			.signWith(secretKey)
			// 위 내용을 암호화해서 JWT를 반환
			.compact();
		return jwt;
	}
	
	public boolean validateJwt(String jwt) {
		try {
			JwtParser jwtParser = Jwts.parser()
				.verifyWith(secretKey)
				.build();
			Jws<Claims> jws = jwtParser.parseSignedClaims(jwt);
			return true;
		} catch(ExpiredJwtException e) {
			e.printStackTrace();
			log.info("기간이 만료된 토큰");
		} catch(SecurityException e) {
			log.info("잘못 서명된 토큰");
		}
		return false;
	}
	
	public Map<String, String> getClaims(String jwt) {
		JwtParser jwtParser = Jwts.parser()
				.verifyWith(secretKey)
				.build();
		Jws<Claims> jws = jwtParser.parseSignedClaims(jwt);
		Claims claims = jws.getPayload();
		
		Map<String, String> map = new HashMap<>();
		map.put("mid", claims.getSubject());
		map.put("memail", claims.get("memail").toString());		
		return map;
	}
}








