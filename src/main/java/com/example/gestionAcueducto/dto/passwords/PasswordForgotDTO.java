package com.example.gestionAcueducto.dto.passwords;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;




@Setter
@Getter
@NoArgsConstructor
public class PasswordForgotDTO {
	@NotBlank(message = "LA DIRECCIÓN DE CORREO ELECTRÓNICO ES REQUERIDA")
	@Email(message = "CORREO ELECTRÓNICO INVÁLIDO")
	private String email;
}
