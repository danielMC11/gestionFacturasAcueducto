package com.example.gestionAcueducto.users.dto;


import com.example.gestionAcueducto.validator.PasswordConfirmation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@PasswordConfirmation(password = "password", confirmPassword = "confirmPassword")
public record PasswordResetRequest (

	@Size(min=8, max=32, message = "La constraseña debe ser al menos de 8 caracteres")
	@NotBlank(message = "La contraseña no puede ser vacía")
	String password,

	@NotBlank(message = "La contraseña no puede ser vacía")
	String confirmPassword,

	@NotBlank(message =  "El token no puede ser vacío")
	String token
){}
