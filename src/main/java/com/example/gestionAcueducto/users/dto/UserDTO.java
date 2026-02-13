package com.example.gestionAcueducto.users.dto;


import com.example.gestionAcueducto.validator.groups.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDTO(

	@Null(groups = {OnCreate.class, OnPutSingleUpdate.class}, message = "El id no debe ser enviado en esta operación")
	Long id,

	@NotBlank(groups = {OnCreate.class, OnPutSingleUpdate.class}, message = "LOS NOMBRES SON REQUERIDOS")
	@Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 carácteres")
	String firstName,

	@NotBlank(groups = {OnCreate.class, OnPutSingleUpdate.class}, message = "LOS APELLIDOS SON REQUERIDOS")
	@Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 carácteres")
	String lastName,

	@NotBlank(groups = {OnCreate.class, OnPutSingleUpdate.class}, message = "EL CORREO ELECTRÓNICO ES REQUERIDO")
	@Email(message = "CORREO ELECTRÓNICO INVALIDO")
	String email,

	@NotBlank(groups = {OnCreate.class, OnPutSingleUpdate.class}, message = "EL NÚMERO DE DOCUMENTO ES REQUERIDO")
	@Size(min = 7, max = 10, message = "EL NÚMERO DE DOCUMENTO DEBE CONTENER ENTRE 7 Y 10 DÍGITOS")
	@Pattern(regexp = "\\d+", message = "EL NÚMERO DE CÉDULA SOLO DEBE CONTENER NÚMEROS")
	String document,

	@NotBlank(groups = {OnCreate.class, OnPutSingleUpdate.class}, message = "EL NÚMERO DE DOCUMENTO ES REQUERIDO")
	@Size(min=4, max=254, message = "DIRECCIÓN INVALIDA")
	String address,

	@NotBlank(groups = {OnCreate.class, OnPutSingleUpdate.class}, message = "EL NÚMERO DE CELULAR ES REQUERIDO")
	@Size(min=10, max=10, message = "EL NÚMERO DE CELULAR DEBE CONTENER EXACTAMENTE 10 DIGITOS")
	@Pattern(regexp = "^(3[0-2])[0-9]{8}$", message = "NÚMERO DE CELULAR INVALIDO")
	String phoneNumber

){}
