package com.example.gestionAcueducto.auth.controller;


import com.example.gestionAcueducto.auth.dto.SimpleMessageDTO;
import com.example.gestionAcueducto.auth.dto.PasswordForgotRequest;
import com.example.gestionAcueducto.auth.entity.PasswordResetToken;
import com.example.gestionAcueducto.users.entity.User;
import com.example.gestionAcueducto.auth.service.Impl.EmailServiceImpl;
import com.example.gestionAcueducto.auth.service.Impl.PasswordResetTokenServiceImpl;
import com.example.gestionAcueducto.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/v1/forgot-password")
@RequiredArgsConstructor
@RestController
public class PasswordForgotController {


	private UserService userService;
	private PasswordResetTokenServiceImpl passwordResetTokenServiceImpl;
	private EmailServiceImpl emailServiceImpl;



	@PostMapping
	public ResponseEntity<SimpleMessageDTO> processForgotPassword(@Valid @RequestBody PasswordForgotRequest passwordForgotRequest) {

		User user = userService.findByEmail(passwordForgotRequest.getEmail());

		PasswordResetToken passwordResetToken = passwordResetTokenServiceImpl.findByUser(user);

		if(passwordResetToken != null){

			if(!passwordResetTokenServiceImpl.isTokenExpired(passwordResetToken)){
			emailServiceImpl.sendResetPasswordEmail(user.getEmail(), passwordResetToken.getToken());
			return ResponseEntity.ok(new SimpleMessageDTO("Se ha reenviado el correo exitosamente!"));
			}
			passwordResetTokenServiceImpl.deleteToken(passwordResetToken);
		}

		String token = passwordResetTokenServiceImpl.createTokenForUser(user);

		emailServiceImpl.sendResetPasswordEmail(user.getEmail(), token);

		return ResponseEntity.ok(new SimpleMessageDTO("Se ha generado un nuevo correo de recuperación exitosamente!"));

	}

}
