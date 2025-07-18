package com.example.gestionAcueducto.auth.controller;

import com.example.gestionAcueducto.auth.dto.SimpleMessageDTO;
import com.example.gestionAcueducto.auth.dto.PasswordResetRequest;
import com.example.gestionAcueducto.auth.entity.PasswordResetToken;
import com.example.gestionAcueducto.users.entity.User;
import com.example.gestionAcueducto.auth.service.PasswordResetTokenService;
import com.example.gestionAcueducto.exceptions.domain.TokenExpiredException;
import com.example.gestionAcueducto.users.service.UserService;
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
	public ResponseEntity<SimpleMessageDTO> handlePasswordReset(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {


		PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(passwordResetRequest.getToken());

		if(passwordResetTokenService.isTokenExpired(passwordResetToken)){
			passwordResetTokenService.deleteToken(passwordResetToken);
			throw new TokenExpiredException("EL TOKEN DE RESTABLECIMIENTO ESTÁ VENCIDO, SOLICITE UN NUEVO CORREO DE RECUPERACIÓN");
		}

		User user = passwordResetToken.getUser();
		userService.updatePassword(passwordResetRequest.getPassword(), user.getId());

		passwordResetTokenService.deleteToken(passwordResetToken);

		return ResponseEntity.ok(new SimpleMessageDTO("CONTRASEÑA RESTABLECIDA EXITOSAMENTE!"));
	}

}
