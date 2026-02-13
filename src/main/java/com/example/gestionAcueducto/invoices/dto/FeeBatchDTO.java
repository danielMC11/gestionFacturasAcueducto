package com.example.gestionAcueducto.invoices.dto;

import com.example.gestionAcueducto.validator.groups.OnPutBatchUpdate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public record FeeBatchDTO(
        @Valid
        List<FeeDTO> feesDto
) {}