package com.vyle.TanKy.util;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.vyle.TanKy.service.auth.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtils {

	@Value("${loginUser.token.secretKey}")
	private String jwtSecret;
	
	@Value("${loginUser.token.expiredTime}")
	private int jwtExpiry;
	
	@Value("${loginUser.jwtCookieName}")
	private String jwtCookie;
	
	public String getJwtCookies(HttpServletRequest request) {
		Cookie cookie = WebUtils.getCookie(request, jwtCookie);
		if(cookie != null) return cookie.getValue();
		else return null;
	}
	
	public ResponseCookie generateJwtCookie(UserDetailsImpl userDetail) {
		String jwt = generateTokenFromUserName(userDetail.getUsername());
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/").maxAge(24*60*60).httpOnly(true).build();
		return cookie;
		
	}
	
	public String generateTokenFromUserName(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpiry))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public ResponseCookie getCleanJwtCookie() {
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/").build();
		return cookie;
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			System.out.println("Invalid signature: {}" + e.getMessage());
		}catch (MalformedJwtException e) {
			System.out.println("Invalid jwt token: {}" + e.getMessage());
		}catch (ExpiredJwtException e) {
			System.out.println("Expired token: {}" + e.getMessage());
		}catch (IllegalArgumentException e) {
			System.out.println("Jwt claims string is empty: {}" + e.getMessage());
		}
		return false;
	}
}
