package com.example.gestionAcueducto.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
