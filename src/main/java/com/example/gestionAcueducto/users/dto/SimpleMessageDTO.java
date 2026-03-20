package com.example.gestionAcueducto.users.dto;

import jakarta.validation.constraints.NotBlank;

public record SimpleMessageDTO(@NotBlank String message) {}
