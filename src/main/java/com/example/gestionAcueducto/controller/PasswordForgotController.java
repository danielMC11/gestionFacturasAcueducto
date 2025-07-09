package com.example.gestionAcueducto.controller;


import com.example.gestionAcueducto.dto.SimpleMessageDTO;
import com.example.gestionAcueducto.dto.passwords.PasswordForgotDTO;
import com.example.gestionAcueducto.entity.PasswordResetToken;
import com.example.gestionAcueducto.entity.User;
import com.example.gestionAcueducto.service.EmailServiceImpl;
import com.example.gestionAcueducto.service.PasswordResetTokenService;
import com.example.gestionAcueducto.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/forgot-password")
@RestController
public class PasswordForgotController {


	private UserService userService;
	private PasswordResetTokenService passwordResetTokenService;
	private EmailServiceImpl emailServiceImpl;



	@PostMapping
	public ResponseEntity<SimpleMessageDTO> processForgotPassword(@Valid @RequestBody PasswordForgotDTO passwordForgotDTO) {

		User user = userService.findByEmail(passwordForgotDTO.getEmail());

		PasswordResetToken passwordResetToken = passwordResetTokenService.findByUser(user);

		if(passwordResetToken != null){

			if(!passwordResetTokenService.isTokenExpired(passwordResetToken)){
			emailServiceImpl.sendResetPasswordEmail(user.getEmail(), passwordResetToken.getToken());
			return ResponseEntity.ok( new SimpleMessageDTO("Se ha reenviado el correo exitosamente!"));
			}
			passwordResetTokenService.deleteToken(passwordResetToken);
		}

		String token = passwordResetTokenService.createTokenForUser(user);

		emailServiceImpl.sendResetPasswordEmail(user.getEmail(), token);

		return ResponseEntity.ok(new SimpleMessageDTO("Se ha generado un nuevo correo de recuperación exitosamente!"));

	}

}
