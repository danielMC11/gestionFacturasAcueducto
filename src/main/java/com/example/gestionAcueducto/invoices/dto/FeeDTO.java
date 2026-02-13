package com.example.gestionAcueducto.invoices.dto;


import com.example.gestionAcueducto.validator.NotBlankCustom;
import com.example.gestionAcueducto.validator.groups.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.math.BigDecimal;

public record FeeDTO(
    @NotNull(groups = {OnPatchBatchUpdate.class}, message = "El id debe ser enviado en esta operación")
    Long id,

    @NotBlank(groups = {OnPutBatchUpdate.class}, message = "La descripción es requerida")
    String description,

    @NotNull(groups = {OnPutBatchUpdate.class}, message = "El precio unitario es requerido")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    BigDecimal unitPrice
) {}
