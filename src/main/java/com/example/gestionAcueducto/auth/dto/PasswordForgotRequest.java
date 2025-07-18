package com.example.gestionAcueducto.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Setter
@Getter
@NoArgsConstructor
public class PasswordForgotRequest {
	@NotBlank(message = "LA DIRECCIÓN DE CORREO ELECTRÓNICO ES REQUERIDA")
	@Email(message = "CORREO ELECTRÓNICO INVÁLIDO")
	private String email;
}
