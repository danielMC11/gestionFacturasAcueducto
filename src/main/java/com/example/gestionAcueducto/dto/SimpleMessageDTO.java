package com.example.gestionAcueducto.dto;

import jakarta.validation.constraints.NotBlank;

public record SimpleMessageDTO(@NotBlank String message) {}
