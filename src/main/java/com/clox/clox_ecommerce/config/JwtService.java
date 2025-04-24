package com.clox.clox_ecommerce.config;

import com.clox_ecommerce.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String SECRET_KEY;

	private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

	// Generate JWT Token
	public String generateToken(User user) {
		return Jwts.builder()
				.setSubject(user.getEmail())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}

	// Validate token
	public boolean isTokenValid(String token) {
		try {
			Jwts.parser()
					.setSigningKey(SECRET_KEY)
					.parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
