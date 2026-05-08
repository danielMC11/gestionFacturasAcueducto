package com.altamira.invoices.service;

import com.altamira.invoices.dto.FeeDTO;

import java.util.List;

public interface FeeService {

    List<FeeDTO> getAllFees();

    void updateFees(List<FeeDTO> fees);

}
