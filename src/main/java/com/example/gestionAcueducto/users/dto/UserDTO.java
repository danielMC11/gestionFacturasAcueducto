package com.example.gestionAcueducto.users.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDTO(

	@NotBlank(message = "El nombre no puede ser vacío")
	@Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 carácteres")
	String firstName,

	@NotBlank(message = "El apellido no puede ser vacío")
	@Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 carácteres")
	String lastName,

	@NotBlank(message = "La dirección de correo electrónico es requerida")
	@Email(message = "Correo electrónico inválido")
	String email,

	@Size(min = 7, max = 10, message = "El número de cédula debe tener entre 7 y 10 dígitos.")
	@Pattern(regexp = "\\d+", message = "El número de cédula solo debe contener dígitos.")
	String document,

	@Size(min=4, max=254, message = "Dirección inválida")
	String address,

	@Size(min=10, max=10, message = "El número de teléfono debe tener exactamente 10 dígitos")
	@Pattern(regexp = "^(3[0-2])[0-9]{8}$", message = "Número de teléfono inválido")
	String phoneNumber


){}
