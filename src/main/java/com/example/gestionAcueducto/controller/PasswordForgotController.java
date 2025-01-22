package com.example.gestionAcueducto.controller;


import com.example.gestionAcueducto.dto.PasswordForgotDTO;
import com.example.gestionAcueducto.entity.User;
import com.example.gestionAcueducto.service.EmailService;
import com.example.gestionAcueducto.service.PasswordResetTokenService;
import com.example.gestionAcueducto.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@AllArgsConstructor
@RequestMapping("/forgot-password")
@Controller
public class PasswordForgotController {

	private UserService userService;
	private PasswordResetTokenService passwordResetTokenService;
	private EmailService emailService;



	@ModelAttribute("forgotPasswordForm")
	public PasswordForgotDTO passwordForgotDTO(){
		return new PasswordForgotDTO();
	}

	@GetMapping
	public String displayForgotPasswordPage() {
		return "password-forms/forgot-password";
	}

	@PostMapping
	public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDTO form, BindingResult result) {

		if(result.hasErrors()){
			return "password-forms/forgot-password";
		}

		User user = userService.findByEmail(form.getEmail());

		if(user == null){
			result.rejectValue("email", null, "CORREO ELECTRONICO NO REGISTRADO");
			return "password-forms/forgot-password";
		}

		String token = passwordResetTokenService.createTokenForUser(user);

		emailService.sendResetPasswordEmail(user.getEmail(), token);

		return "redirect:/forgot-password?success";

	}





}
