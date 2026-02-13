package com.example.gestionAcueducto.invoices.service;

import com.example.gestionAcueducto.invoices.dto.FeeDTO;

import java.util.List;

public interface FeeService {

    List<FeeDTO> getAllFees();

    void updateFees(List<FeeDTO> fees);

}
