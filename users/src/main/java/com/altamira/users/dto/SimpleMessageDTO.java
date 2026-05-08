package com.altamira.users.dto;

import jakarta.validation.constraints.NotBlank;

public record SimpleMessageDTO(@NotBlank String message) {}
