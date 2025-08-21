package com.example.gestionAcueducto.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




public record PasswordForgotRequest(
		@NotBlank(message = "La dirección de correo electrónico es requerida")
		@Email(message = "Correo electrónico inválido")
		String email
){}
