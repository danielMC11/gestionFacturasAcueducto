package com.example.gestionAcueducto.auth.controller;

import com.example.gestionAcueducto.auth.dto.LoginRequest;
import com.example.gestionAcueducto.auth.dto.LoginResponse;
import com.example.gestionAcueducto.security.jwt.JwtUtils;
import com.example.gestionAcueducto.users.entity.User;
import com.example.gestionAcueducto.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;



	@PostMapping("login")
	public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String email = userDetails.getUsername();

		ResponseCookie accessCookie = jwtUtils.generateAccessCookie(email);
		ResponseCookie refreshCookie = jwtUtils.generateRefreshCookie(email);

		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, accessCookie.toString())
				.header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
				.body(new LoginResponse(
						userDetails.getUsername(),
						roles));
	}


	@PostMapping("refresh")
	public ResponseEntity<LoginResponse> refresh(HttpServletRequest request) {
		String refreshToken = jwtUtils.getRefreshTokenFromCookie(request);

		if(jwtUtils.isRefreshToken(refreshToken) && !jwtUtils.isTokenExpired(refreshToken)){
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			String email = userDetails.getUsername();
			ResponseCookie accessCookie = jwtUtils.generateAccessCookie(email);
			ResponseCookie refreshCookie = jwtUtils.generateRefreshCookie(email);

			List<String> roles = userDetails.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());

			return ResponseEntity.ok()
					.header(HttpHeaders.SET_COOKIE, accessCookie.toString())
					.header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
					.body(new LoginResponse(
							userDetails.getUsername(),
							roles));
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}


	@PostMapping("logout")
	public ResponseEntity<Void> logout(){
		String username =  ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		ResponseCookie accessCookie= jwtUtils.cleanAccessToken();
		ResponseCookie refreshCookie = jwtUtils.cleanRefreshToken(
				userService.findByEmail(username)
		);

		SecurityContextHolder.clearContext();

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, accessCookie.toString())
				.header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
				.build();

	}

}
