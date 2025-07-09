package com.example.gestionAcueducto.controller;

import com.example.gestionAcueducto.dto.SimpleMessageDTO;
import com.example.gestionAcueducto.dto.passwords.PasswordResetDTO;
import com.example.gestionAcueducto.entity.PasswordResetToken;
import com.example.gestionAcueducto.entity.User;
import com.example.gestionAcueducto.exceptions.domain.TokenExpiredException;
import com.example.gestionAcueducto.service.PasswordResetTokenService;
import com.example.gestionAcueducto.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/reset-password")
@AllArgsConstructor
@RestController
public class PasswordResetController {


	private UserService userService;
	private PasswordResetTokenService passwordResetTokenService;


	@PostMapping
	public ResponseEntity<SimpleMessageDTO> handlePasswordReset(@Valid @RequestBody PasswordResetDTO passwordResetDTO) {


		PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(passwordResetDTO.getToken());

		if(passwordResetTokenService.isTokenExpired(passwordResetToken)){
			passwordResetTokenService.deleteToken(passwordResetToken);
			throw new TokenExpiredException("EL TOKEN DE RESTABLECIMIENTO ESTÁ VENCIDO, SOLICITE UN NUEVO CORREO DE RECUPERACIÓN");
		}

		User user = passwordResetToken.getUser();
		userService.updatePassword(passwordResetDTO.getPassword(), user.getUserId());

		passwordResetTokenService.deleteToken(passwordResetToken);

		return ResponseEntity.ok(new SimpleMessageDTO("CONTRASEÑA RESTABLECIDA EXITOSAMENTE!"));
	}

}
