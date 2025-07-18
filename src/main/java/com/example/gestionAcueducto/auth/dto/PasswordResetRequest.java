package com.example.gestionAcueducto.auth.dto;


import com.example.gestionAcueducto.validator.PasswordConfirmation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@PasswordConfirmation(password = "password", confirmPassword = "confirmPassword")

public class PasswordResetRequest {

	@Size(min=8, max=32, message = "La constraseña debe ser al menos de 8 caracteres")
	@NotBlank(message = "La contraseña no puede ser vacía")
	private String password;

	@NotBlank(message = "La contraseña no puede ser vacía")
	private String confirmPassword;

	@NotBlank(message =  "El token no puede ser vacío")
	private String token;

}
