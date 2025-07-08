package com.example.gestionAcueducto.dto.passwords;


import com.example.gestionAcueducto.validator.PasswordConfirmation;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@PasswordConfirmation(password = "password", confirmPassword = "confirmPassword")
public class PasswordResetDTO {

	@NotNull
	@Size(min=8, max=32, message = "La constraseña debe ser al menos de 8 caracteres")
	private String password;

	@NotNull
	private String confirmPassword;
	@Column(unique = true)
	private String token;

}
