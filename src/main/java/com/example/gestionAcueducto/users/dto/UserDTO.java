package com.example.gestionAcueducto.users.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

	@NotBlank(message = "El nombre no puede ser vacío")
	@Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 carácteres")
	private String firstName;

	@NotBlank(message = "El apellido no puede ser vacío")
	@Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 carácteres")
	private String lastName;

	@NotBlank(message = "El número de documento es requerido")
	@Size(min = 7, max = 10, message = "El número de cédula debe tener entre 7 y 10 dígitos.")
	@Pattern(regexp = "\\d+", message = "El número de cédula solo debe contener dígitos.")
	private String document;

	@NotBlank(message = "La dirección de correo electrónico es requerida")
	@Email(message = "Correo electrónico inválido")
	private String email;

	@NotBlank(message = "La dirección no puede ser vacío")
	@Size(min=4, max=254, message = "Dirección inválida")
	private String address;

	//@NotBlank(message = "El número no puede ser vacío")
	@Size(min=10, max=10, message = "El número de teléfono debe tener exactamente 10 dígitos")
	@Pattern(regexp = "^(3[0-2])[0-9]{8}$", message = "Número de teléfono inválido")
	private String phoneNumber;
}
