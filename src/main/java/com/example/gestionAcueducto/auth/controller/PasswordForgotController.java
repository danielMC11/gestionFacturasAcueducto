package com.example.gestionAcueducto.auth.controller;


import com.example.gestionAcueducto.auth.dto.SimpleMessageDTO;
import com.example.gestionAcueducto.auth.dto.PasswordForgotRequest;
import com.example.gestionAcueducto.auth.entity.PasswordResetToken;
import com.example.gestionAcueducto.auth.service.PasswordResetTokenService;
import com.example.gestionAcueducto.users.entity.User;
import com.example.gestionAcueducto.auth.service.Impl.EmailServiceImpl;
import com.example.gestionAcueducto.users.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/forgot-password")
@RequiredArgsConstructor
@RestController
public class PasswordForgotController {


	private final UserService userService;
	private final PasswordResetTokenService passwordResetTokenService;
	private final EmailServiceImpl emailServiceImpl;



	@PostMapping
	public ResponseEntity<SimpleMessageDTO> processForgotPassword(@Valid @RequestBody PasswordForgotRequest passwordForgotRequest) {

		User user = userService.findByEmail(passwordForgotRequest.email());

		PasswordResetToken passwordResetToken = passwordResetTokenService.findByUser(user);

		if(passwordResetToken != null){

			if(!passwordResetTokenService.isTokenExpired(passwordResetToken)){
			emailServiceImpl.sendResetPasswordEmail(user.getEmail(), "reset-password-email",passwordResetToken.getToken());
			return ResponseEntity.ok(new SimpleMessageDTO("Se ha reenviado el correo exitosamente!"));
			}
			passwordResetTokenService.deleteToken(passwordResetToken);
		}

		String token = passwordResetTokenService.createTokenForUser(user, 5);

		emailServiceImpl.sendResetPasswordEmail(user.getEmail(), "reset-password-email", token);

		return ResponseEntity.ok(new SimpleMessageDTO("Se ha generado un nuevo correo de recuperación exitosamente!"));

	}

}
