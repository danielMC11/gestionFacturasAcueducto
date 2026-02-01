package com.example.gestionAcueducto.auth.controller;

import com.example.gestionAcueducto.auth.dto.LoginRequest;
import com.example.gestionAcueducto.auth.dto.AuthResponse;
import com.example.gestionAcueducto.exceptions.domain.NoRoleFoundException;
import com.example.gestionAcueducto.exceptions.domain.NotFoundException;
import com.example.gestionAcueducto.security.jwt.JwtUtils;
import com.example.gestionAcueducto.users.dto.UserDTO;
import com.example.gestionAcueducto.users.entity.User;
import com.example.gestionAcueducto.users.mapper.UserMapper;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final UserDetailsService userDetailsService;



	@PostMapping("login")
	public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String email = userDetails.getUsername();

		ResponseCookie accessCookie = jwtUtils.generateAccessCookie(email);
		ResponseCookie refreshCookie = jwtUtils.generateRefreshCookie(email);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
		headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());

		String role = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.findFirst()
				.orElseThrow(()-> new NoRoleFoundException("NO ROLE ERROR"));

		return ResponseEntity.ok()
				.headers(headers)
				.body(new AuthResponse(
						userDetails.getUsername(),
						role));
	}


	@PostMapping("refresh")
	public ResponseEntity<AuthResponse> refresh(HttpServletRequest request) {
		String refreshToken = jwtUtils.getRefreshTokenFromCookie(request);

		if(refreshToken != null && jwtUtils.isRefreshToken(refreshToken) && !jwtUtils.isTokenExpired(refreshToken)){

			String email = jwtUtils.getEmail(refreshToken);

			UserDetails userDetails = userDetailsService.loadUserByUsername(email);

			ResponseCookie accessCookie = jwtUtils.generateAccessCookie(email);
			ResponseCookie refreshCookie = jwtUtils.generateRefreshCookie(email);

			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
			headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());

			String role = userDetails.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.findFirst()
					.orElseThrow(()-> new NoRoleFoundException("NO ROLE ERROR"));

			return ResponseEntity.ok()
					.headers(headers)
					.body(new AuthResponse(
							userDetails.getUsername(),
							role));
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

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
		headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());


		SecurityContextHolder.clearContext();

		return ResponseEntity.ok()
				.headers(headers)
				.build();

	}

	@GetMapping("current-user")
	public ResponseEntity<AuthResponse> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails){

		String email = userDetails.getUsername();
		String role = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.findFirst()
				.orElseThrow(()-> new NoRoleFoundException("NO ROLE ERROR"));

		return ResponseEntity.ok(new AuthResponse(email, role));


	}

}
