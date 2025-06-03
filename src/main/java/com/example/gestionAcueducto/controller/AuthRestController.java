package com.example.gestionAcueducto.controller;

import com.example.gestionAcueducto.dto.authentication.LoginRequest;
import com.example.gestionAcueducto.dto.UserInfoDTO;
import com.example.gestionAcueducto.dto.authentication.UserInfoResponse;
import com.example.gestionAcueducto.security.jwt.JwtUtils;
import com.example.gestionAcueducto.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/auth/")
public class AuthRestController {

	private UserService userService;
	private AuthenticationManager authenticationManager;
	private JwtUtils jwtUtils;


	@PostMapping("login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

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
				.body(new UserInfoResponse(
						userDetails.getUsername(),
						roles));
	}


	@PostMapping("refresh")
	public ResponseEntity<?> refresh(HttpServletRequest request) {
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
					.body(new UserInfoResponse(
							userDetails.getUsername(),
							roles));
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}


	@PostMapping("logout")
	public ResponseEntity<?> logout(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ResponseCookie accessCookie= jwtUtils.cleanAccessToken();
		ResponseCookie refreshCookie = jwtUtils.cleanRefreshToken(
				userService.findByEmail(userDetails.getUsername())
		);

		SecurityContextHolder.clearContext();

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, accessCookie.toString())
				.header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
				.body(
						"Logout successful"
				);

	}


	@PostMapping("register")
	public String crear(@RequestBody UserInfoDTO userInfoDTO){
		try {
			userService.createUser(userInfoDTO);
			return "Bien";
		}catch (Exception e){
			e.printStackTrace();
			return "mal";
		}
	}


	@GetMapping("data")
	public ResponseEntity<List<String>> testData(){
		List<String> data = new ArrayList<String>(List.of(
				"Lio mesi",
				"Lamine Yamal",
				"Cole palmer"
		));

		return ResponseEntity.ok(data);
	}



}
