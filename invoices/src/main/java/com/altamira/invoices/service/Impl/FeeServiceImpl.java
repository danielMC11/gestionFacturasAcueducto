package com.altamira.invoices.service.Impl;

import com.altamira.common.exceptions.domain.EmptyException;
import com.altamira.invoices.dto.FeeDTO;
import com.altamira.invoices.entity.Fee;
import com.altamira.invoices.mapper.FeeMapper;
import com.altamira.invoices.repository.FeeRepository;
import com.altamira.invoices.service.FeeService;
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
