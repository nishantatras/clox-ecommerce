package com.clox.clox_ecommerce.auth;

import com.clox.clox_ecommerce.user.User;
import com.clox.clox_ecommerce.user.UserRepository;
import com.clox.clox_ecommerce.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtService jwtService;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// Register
	public String register(RegisterRequest request) {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new IllegalStateException("User already exists");
		}

		User user = User.builder()
				.name(request.getName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.USER) // Default to USER role
				.build();
		userRepository.save(user);

		return jwtService.generateToken(user);
	}

	// Login
	public String login(AuthRequest request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalStateException("User not found"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new IllegalStateException("Invalid credentials");
		}

		return jwtService.generateToken(user);
	}
}
