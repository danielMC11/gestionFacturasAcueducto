package com.altamira.invoices.dto;

import jakarta.validation.Valid;


import java.util.List;

public record FeeBatchDTO(
        @Valid
        List<FeeDTO> feesDto
) {}