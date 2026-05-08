package com.altamira.invoices.dto;


import com.altamira.common.validator.NotBlankCustom;
import com.altamira.common.validator.groups.OnPatchBatchUpdate;
import com.altamira.common.validator.groups.OnPutBatchUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


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
