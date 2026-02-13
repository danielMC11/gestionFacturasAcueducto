package com.example.gestionAcueducto.invoices.service.Impl;

import com.example.gestionAcueducto.exceptions.domain.EmptyException;
import com.example.gestionAcueducto.invoices.dto.FeeDTO;
import com.example.gestionAcueducto.invoices.entity.Fee;
import com.example.gestionAcueducto.invoices.mapper.FeeMapper;
import com.example.gestionAcueducto.invoices.repository.FeeRepository;
import com.example.gestionAcueducto.invoices.service.FeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class FeeServiceImpl implements FeeService {

    private final FeeRepository feeRepository;
    private final FeeMapper feeMapper;

    @Override
    public List<FeeDTO> getAllFees(){
        return feeRepository.findAllByOrderByIdAsc().stream().map(feeMapper::entityToDto).toList();
    }

    @Override
    @Transactional
    public void updateFees(List<FeeDTO> feesDtos) {

        if (feesDtos == null || feesDtos.isEmpty()) {
            throw new EmptyException("La lista de actualización no puede estar vacía.");
        }

        List<Long> ids = feesDtos.stream().map(FeeDTO::id).toList();
        List<Fee> existingEntities = feeRepository.findAllById(ids);

        for (FeeDTO dto : feesDtos) {
            existingEntities.stream()
                    .filter(entity -> entity.getId().equals(dto.id()))
                    .findFirst()
                    .ifPresent(entity -> feeMapper.updateEntityFromDto(dto, entity));
        }


        feeRepository.saveAll(existingEntities);

    }
}
