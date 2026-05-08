package com.altamira.users.controller;


import com.altamira.users.dto.PasswordForgotRequest;
import com.altamira.users.service.PasswordForgotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/forgot-password")
@RequiredArgsConstructor
@RestController
public class PasswordForgotController {


	private final PasswordForgotService passwordForgotService;



	@PostMapping
	public ResponseEntity<String> processForgotPassword(@Valid @RequestBody PasswordForgotRequest passwordForgotRequest) {
		return ResponseEntity.ok(passwordForgotService.processForgotPassword(passwordForgotRequest));
	}

}
