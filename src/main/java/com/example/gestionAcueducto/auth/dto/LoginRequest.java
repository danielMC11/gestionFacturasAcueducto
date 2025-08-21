package com.example.gestionAcueducto.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public record LoginRequest (

        @NotBlank(message = "La dirección de correo electrónico es requerida")
        @Email(message = "Correo electrónico inválido")
        String email,
        @NotBlank(message = "La contraseña es requerida")
        String password
){}
