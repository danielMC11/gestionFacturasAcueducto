package com.example.gestionAcueducto.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record SimpleMessageDTO(@NotBlank String message) {}
