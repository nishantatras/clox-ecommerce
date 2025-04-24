package com.clox.clox_ecommerce.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	// Register endpoint
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
		String token = authService.register(request);
		return ResponseEntity.ok(token);
	}

	// Login endpoint
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody AuthRequest request) {
		String token = authService.login(request);
		return ResponseEntity.ok(token);
	}
}
