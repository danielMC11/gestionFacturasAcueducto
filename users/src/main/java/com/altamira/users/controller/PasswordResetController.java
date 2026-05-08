package com.altamira.users.controller;

import com.altamira.users.dto.PasswordResetRequest;
import com.altamira.users.entity.PasswordResetToken;
import com.altamira.users.entity.User;
import com.altamira.users.service.PasswordResetTokenService;
import com.altamira.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api/v1/reset-password")
@RequiredArgsConstructor
@RestController
public class PasswordResetController {


	private final UserService userService;
	private final PasswordResetTokenService passwordResetTokenService;


	@PostMapping
	public ResponseEntity<Void> handlePasswordReset(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {


		PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(passwordResetRequest.token());

		if(passwordResetTokenService.isTokenExpired(passwordResetToken)){
			passwordResetTokenService.deleteToken(passwordResetToken);
			throw new RuntimeException("EL TOKEN DE RESTABLECIMIENTO ESTÁ VENCIDO, SOLICITE UN NUEVO CORREO DE RECUPERACIÓN");
		}	

		User user = passwordResetToken.getUser();
		userService.updatePassword(passwordResetRequest.password(), user.getId());

		passwordResetTokenService.deleteToken(passwordResetToken);

		return ResponseEntity.ok().build();
	}

}
