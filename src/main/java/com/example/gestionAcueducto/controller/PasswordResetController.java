package com.example.gestionAcueducto.controller;

import com.example.gestionAcueducto.dto.PasswordResetDTO;
import com.example.gestionAcueducto.entity.PasswordResetToken;
import com.example.gestionAcueducto.entity.User;
import com.example.gestionAcueducto.service.PasswordResetTokenService;
import com.example.gestionAcueducto.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/reset-password")
@AllArgsConstructor
public class PasswordResetController {

	private UserService userService;
	private PasswordResetTokenService passwordResetTokenService;


	@ModelAttribute("passwordResetForm")
	public PasswordResetDTO passwordResetDTO(){
		return new PasswordResetDTO();
	}

	@GetMapping
	public String displayResetPasswordPage(@RequestParam(required = false) String token,
																				 Model model) {

		PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);

		if(passwordResetToken == null){
			model.addAttribute("errorToken", "EL TOKEN NO EXISTE");
		} else if(passwordResetTokenService.isTokenExpired(passwordResetToken)){
			model.addAttribute("errorToken", "EL TOKEN HA EXPIRADO");
		} else {
			model.addAttribute("token", token);
		}

		if (!model.containsAttribute("passwordResetForm")) {
			model.addAttribute("passwordResetForm", new PasswordResetDTO());
		}

		return "password-forms/reset-password";
	}

	@PostMapping
	@Transactional
	public String handlePasswordReset(@ModelAttribute("passwordResetForm") @Valid PasswordResetDTO form, BindingResult result, RedirectAttributes attributes) {

		if(result.hasErrors()){
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.passwordResetForm", result);
			attributes.addFlashAttribute("passwordResetForm", form);

			return "redirect:/reset-password?token=" + form.getToken();
		}
		PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(form.getToken());

		User user = passwordResetToken.getUser();
		userService.updatePassword(form.getPassword(), user.getUserId());
		passwordResetTokenService.deleteToken(passwordResetToken);

		return "redirect:/login?resetSuccess";
	}

}
